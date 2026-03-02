import os
import csv
import re
import math
from collections import defaultdict
from datetime import datetime
from string import Template
from zoneinfo import ZoneInfo

# ==========================================================
# CONFIG
# ==========================================================
FEATURE_DIR = os.getenv("FEATURE_DIR", "src/test/resources/features")
PLANNED_CSV = os.getenv("PLANNED_CSV", "src/test/resources/features/planned-automation-tests.csv")

OUTPUT_MD = "artifacts/feature-coverage.md"
OUTPUT_HTML = "artifacts/feature-coverage.html"

MD_TEMPLATE_PATH = "scripts/test-coverage-report/templates/test-coverage.md.tpl"
HTML_TEMPLATE_PATH = "scripts/test-coverage-report/templates/test-coverage.html.tpl"

# ==========================================================
# REGEX PATTERNS
# ==========================================================
feature_pattern = re.compile(r'^\s*Feature:\s*(.+)')
scenario_pattern = re.compile(r'^\s*Scenario:')
scenario_outline_pattern = re.compile(r'^\s*Scenario Outline:')
examples_pattern = re.compile(r'^\s*Examples:')
table_row_pattern = re.compile(r'^\s*\|')


# ==========================================================
# HELPERS
# ==========================================================
def normalize(name: str) -> str:
    return name.strip().upper()


def get_coverage_value(percent_str: str) -> int:
    return int(percent_str.replace('%', ''))


def get_coverage_color(value: int) -> str:
    if value < 80:
        return "red"
    elif value < 90:
        return "orange"
    return "green"


def format_coverage_md(percent_str: str) -> str:
    value = get_coverage_value(percent_str)
    if value < 80:
        icon = "🔴"
    elif value < 90:
        icon = "🟠"
    else:
        icon = "🟢"
    return f"{icon} {percent_str}"


def load_template(path: str) -> Template:
    if not os.path.exists(path):
        raise FileNotFoundError(f"Template not found: {path}")
    with open(path, encoding="utf-8") as f:
        return Template(f.read())


# ==========================================================
# MAIN LOGIC
# ==========================================================
def main():
    automated = defaultdict(int)
    planned = {}
    csv_feature_display = {}

    # ======================================================
    # GENERATED TIME
    # ======================================================
    now = datetime.now(tz=ZoneInfo("UTC")).astimezone(ZoneInfo("Asia/Singapore"))
    generated_at = now.strftime("%d %b %Y | %I:%M %p").lstrip("0")

    # ======================================================
    # READ PLANNED AUTOMATION TESTS CSV FILE
    # ======================================================
    if not os.path.exists(PLANNED_CSV):
        print("Planned CSV not found!")
        return

    with open(PLANNED_CSV, newline='', encoding="utf-8") as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            original_name = row["Feature"].strip()
            normalized_name = normalize(original_name)

            planned[normalized_name] = int(row["Planned Automation Test Count"])
            csv_feature_display[normalized_name] = original_name

    # ======================================================
    # SCAN FEATURE FILES
    # ======================================================
    for root, _, files in os.walk(FEATURE_DIR):
        for file in files:
            if not file.endswith(".feature"):
                continue

            current_feature = None
            in_scenario_outline = False
            in_examples = False
            example_row_count = 0

            with open(os.path.join(root, file), encoding="utf-8") as f:
                for line in f:
                    stripped = line.strip()

                    feature_match = feature_pattern.match(line)
                    if feature_match:
                        current_feature = normalize(feature_match.group(1))
                        continue

                    if not current_feature:
                        continue

                    if scenario_outline_pattern.match(line):
                        in_scenario_outline = True
                        in_examples = False
                        example_row_count = 0
                        continue

                    if scenario_pattern.match(line) and not in_scenario_outline:
                        automated[current_feature] += 1
                        continue

                    if in_scenario_outline and examples_pattern.match(line):
                        in_examples = True
                        example_row_count = 0
                        continue

                    if in_examples and table_row_pattern.match(line):
                        example_row_count += 1
                        continue

                    if (
                            in_examples
                            and stripped
                            and not table_row_pattern.match(line)
                            and not stripped.startswith("#")
                    ):
                        automated[current_feature] += max(0, example_row_count - 1)
                        in_examples = False

                if in_scenario_outline and in_examples:
                    automated[current_feature] += max(0, example_row_count - 1)

    # ======================================================
    # MERGE DATA (CSV ORDER PRESERVED)
    # ======================================================
    rows = []
    idx = 1

    for normalized_feature in csv_feature_display.keys():
        display_name = csv_feature_display[normalized_feature]
        total = planned.get(normalized_feature, 0)
        auto = automated.get(normalized_feature, 0)
        missing = max(0, total - auto)
        if total > 0:
            coverage_value = math.ceil((auto / total) * 100)
            coverage = f"{coverage_value}%"
        else:
            coverage_value = None
            coverage = "N/A"

        rows.append((idx, display_name, total, auto, coverage, missing, False))
        idx += 1

    missing_from_csv_features = set()

    for normalized_feature in automated.keys():
        if normalized_feature not in csv_feature_display:
            auto = automated.get(normalized_feature, 0)
            missing_from_csv_features.add(normalized_feature)

            rows.append((
                idx,
                normalized_feature.title(),
                None,        # Planned
                auto,
                "N/A",       # Coverage
                None,        # Missing
                True
            ))
            idx += 1

    has_missing_features = bool(missing_from_csv_features)

    # ======================================================
    # TOTALS
    # ======================================================
    total_planned = sum(r[2] for r in rows if r[2] is not None)
    total_automated = sum(r[3] for r in rows if r[3] is not None)
    total_missing = sum(r[5] for r in rows if r[5] is not None)

    overall_coverage_value = (
        math.ceil((total_automated / total_planned) * 100)
        if total_planned else 0
    )
    overall_coverage_md = format_coverage_md(f"{overall_coverage_value}%")
    overall_color = get_coverage_color(overall_coverage_value)

    # ======================================================
    # BUILD TABLE ROWS
    # ======================================================
    md_table_rows = ""
    html_table_rows = ""

    for row in rows:
        index, name, total, auto, coverage_str, missing, is_missing_csv = row

        display_name_md = f"⚠️ {name}" if is_missing_csv else name

        total_display = total if total is not None else "—"
        missing_display = missing if missing is not None else "—"

        # Markdown rendering
        coverage_md = (
            format_coverage_md(coverage_str)
            if coverage_str != "N/A"
            else "⚠️ N/A"
        )

        md_table_rows += (
            f"| {index} | {display_name_md} | {total_display} | {auto} | {coverage_md} | {missing_display} |\n"
        )

        # HTML rendering
        if coverage_str != "N/A":
            color = get_coverage_color(get_coverage_value(coverage_str))
            coverage_html = f'<span class="badge {color}">{coverage_str}</span>'
        else:
            coverage_html = '<span class="badge grey">N/A</span>'

        display_name_html = (
            f'<span title="Feature not defined in planned-automation-tests.csv">⚠️ {name}</span>'
            if is_missing_csv else name
        )

        html_table_rows += f"""
        <tr>
        <td>{index}</td>
        <td>{display_name_html}</td>
        <td>{total_display}</td>
        <td>{auto}</td>
        <td>{coverage_html}</td>
        <td>{missing_display}</td>
        </tr>
        """

    # ======================================================
    # WARNING FLAG
    # ======================================================
    warning_block_md = ""
    warning_block_html = ""

    if has_missing_features:
        warning_block_md = (
            "> ⚠️ **Attention:** Some feature files exist but are not included in "
            "`planned-automation-tests.csv`. Please define their planned automation test count "
            "to ensure accurate coverage reporting.\n"
        )

        warning_block_html = """
            <div class="warning-box">
            <strong>⚠️ Attention:</strong>
            Some feature files exist but are not included in
            <code class="inline-code">planned-automation-tests.csv</code>.
            Please define their planned automation test count to ensure accurate coverage reporting.
            </div>
        """

    # ======================================================
    # RENDER TEMPLATES
    # ======================================================
    os.makedirs("artifacts", exist_ok=True)

    md_template = load_template(MD_TEMPLATE_PATH)
    html_template = load_template(HTML_TEMPLATE_PATH)

    md_output = md_template.substitute(
        overall_coverage_md=overall_coverage_md,
        total_planned=total_planned,
        total_automated=total_automated,
        total_missing=total_missing,
        generated_at=generated_at,
        table_rows=md_table_rows,
        warning_block=warning_block_md
    )

    html_output = html_template.substitute(
        overall_coverage_value=overall_coverage_value,
        overall_color=overall_color,
        total_planned=total_planned,
        total_automated=total_automated,
        total_missing=total_missing,
        generated_at=generated_at,
        table_rows=html_table_rows,
        warning_block=warning_block_html
    )

    write_reports(md_output, html_output)


def write_reports(markdown_output, html_output):
    os.makedirs("artifacts", exist_ok=True)

    with open(OUTPUT_MD, "w", encoding="utf-8") as f:
        f.write(markdown_output)

    with open(OUTPUT_HTML, "w", encoding="utf-8") as f:
        f.write(html_output)

    print("Test coverage reports generated successfully.")


if __name__ == "__main__":
    main()