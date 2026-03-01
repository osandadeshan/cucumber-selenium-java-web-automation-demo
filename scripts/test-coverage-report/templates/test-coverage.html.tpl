<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Automation Test Coverage Report</title>
<link rel="icon" type="image/png" href="https://i.imgur.com/Nsnq46f.png">

<style>
/* ======= YOUR FULL EXACT CSS (UNCHANGED) ======= */
html, body { height:100%; margin:0; }
body {
    background:#111827;
    font-family:Arial, sans-serif;
    color:#f9fafb;
    display:flex;
    flex-direction:column;
    overflow:hidden;
}
.container {
    flex:1;
    display:flex;
    flex-direction:column;
    padding:30px;
    box-sizing:border-box;
    height:100vh;
}
.card-wrapper {
    flex:1;
    display:flex;
    flex-direction:column;
    max-width:1600px;
    width:100%;
    margin:0 auto;
    min-height:0;
}
.header-row {
    display:flex;
    justify-content:space-between;
    align-items:center;
}
.header-title {
    display:flex;
    align-items:center;
    gap:12px;
}
.generated-time {
    font-size:13px;
    color:#9ca3af;
}
.summary {
    display:flex;
    gap:20px;
    flex-wrap:wrap;
    margin:20px 0;
}
.card {
    flex:1;
    min-width:180px;
    background:#1f2937;
    padding:18px;
    border-radius:12px;
    border:1px solid #374151;
    text-align:center;
}
.card h3 {
    font-size:46px;
    margin:0;
    font-weight:700;
}
.card div {
    font-size:16px;
    color:#9ca3af;
    margin-top:6px;
}
.overall-progress-container { margin-top:18px; }
.overall-progress-bar {
    width:100%;
    height:18px;
    border-radius:999px;
    background-color:#374151;
}
.top-controls {
    display:flex;
    justify-content:space-between;
    align-items:center;
    margin-bottom:12px;
}
.search-box input {
    width:420px;
    padding:9px 14px;
    border-radius:8px;
    border:1px solid #374151;
    background:#111827;
    color:#f9fafb;
}
.legend { display:flex; gap:20px; font-size:14px; }
.dot {
    height:12px;
    width:12px;
    border-radius:50%;
    display:inline-block;
    margin-right:6px;
}
.table-wrapper {
    flex:1;
    min-height:0;
    border:1px solid #374151;
    border-radius:12px;
    overflow-y:auto;
    background:#1f2937;
}
table { width:100%; border-collapse:collapse; }
thead th {
    position:sticky;
    top:0;
    background:#111827;
    z-index:20;
    border-bottom:2px solid #374151;
    vertical-align:top;
}
th, td {
    padding:14px;
    border-bottom:1px solid #374151;
    text-align:left;
}
tbody tr:hover { background:rgba(255,255,255,0.05); }
th.sortable { cursor:pointer; }
th.sortable .arrow::after {
    content:" ↑↓";
    font-size:12px;
    color:#9ca3af;
}
th.sortable.asc .arrow::after { content:" ↑"; }
th.sortable.desc .arrow::after { content:" ↓"; }
.coverage-filter { margin-top:6px; }
.coverage-filter select {
    width:100%;
    padding:4px 6px;
    font-size:12px;
    border-radius:6px;
    border:1px solid #374151;
    background:#111827;
    color:#f9fafb;
}
.badge {
    padding:5px 12px;
    border-radius:20px;
    font-weight:bold;
    font-size:12px;
    color:white;
}
.green { background:#22c55e; }
.orange { background:#f59e0b; }
.red { background:#ef4444; }
.grey { background: #545454; }
.dot.green { background:#22c55e; }
.dot.orange { background:#f59e0b; }
.dot.red { background:#ef4444; }
.inline-code {
    background-color: #f3f4f6;
    color: #374151;
    padding: 3px 8px;
    border-radius: 6px;
    font-size: 13px;
    font-family: "SFMono-Regular", Consolas, "Liberation Mono", Menlo, monospace;
    border: 1px solid #e5e7eb;
}
</style>

<script>
let originalRows = [];

window.onload = function() {
    const tbody = document.querySelector("#coverageTable tbody");
    originalRows = Array.from(tbody.rows).map(row => row.cloneNode(true));
};

function sortTable(n) {
    if(n === 0) return;

    const table = document.getElementById("coverageTable");
    const tbody = table.querySelector("tbody");
    const headers = table.querySelectorAll("th");
    const currentHeader = headers[n];

    if (currentHeader.classList.contains("asc")) {
        currentHeader.classList.remove("asc");
        currentHeader.classList.add("desc");

    } else if (currentHeader.classList.contains("desc")) {
        headers.forEach(h => h.classList.remove("asc","desc"));

        tbody.innerHTML = "";
        originalRows.forEach(row =>
            tbody.appendChild(row.cloneNode(true))
        );

        filterCoverage();
        updateRowNumbers();
        return;

    } else {
        headers.forEach(h => h.classList.remove("asc","desc"));
        currentHeader.classList.add("asc");
    }

    const asc = currentHeader.classList.contains("asc");
    let rows = Array.from(tbody.rows);

    rows.sort((a,b)=>{
        let x = a.cells[n].innerText.replace('%','').trim();
        let y = b.cells[n].innerText.replace('%','').trim();

        if(!isNaN(x) && !isNaN(y)) {
            return asc ? x - y : y - x;
        }
        return asc ? x.localeCompare(y) : y.localeCompare(x);
    });

    tbody.innerHTML = "";
    rows.forEach(row => tbody.appendChild(row));
    updateRowNumbers();
}

function filterCoverage() {
    const filter = document.getElementById("coverageFilter").value;
    const rows = document.querySelectorAll("#coverageTable tbody tr");

    rows.forEach(row => {
        const badge = row.cells[4].querySelector(".badge");

        if (filter === "all") {
            row.style.display = "";
        } else if (badge.classList.contains(filter)) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });

    updateRowNumbers();
}

function searchFeature() {
    const input = document.getElementById("searchInput").value.toLowerCase();
    const rows = document.querySelectorAll("#coverageTable tbody tr");

    rows.forEach(row=>{
        const feature = row.cells[1].innerText.toLowerCase();
        row.style.display = feature.includes(input) ? "" : "none";
    });

    updateRowNumbers();
}

function updateRowNumbers() {
    const rows = document.querySelectorAll("#coverageTable tbody tr");
    let count = 1;

    rows.forEach(row => {
        if (row.style.display !== "none") {
            row.cells[0].innerText = count++;
        }
    });
}
</script>
</head>

<body>
<div class="container">
<div class="card-wrapper">

<div class="header-row">
  <div class="header-title">
    <img src="https://i.imgur.com/Nsnq46f.png" style="width:28px;height:28px;">
    <h2>Automation Test Coverage Report</h2>
  </div>
  <div class="generated-time">
    Generated: ${generated_at}
  </div>
</div>

<div class="summary">
  <div class="card">
    <h3>${overall_coverage_value}%</h3>
    <div>Overall Coverage</div>
    <div class="overall-progress-container">
        <div class="overall-progress-bar"
             style="background:
             linear-gradient(to right,
                ${overall_color} ${overall_coverage_value}%,
                #374151 ${overall_coverage_value}%);">
        </div>
    </div>
  </div>
  <div class="card"><h3>${total_planned}</h3><div>Total Planned</div></div>
  <div class="card"><h3>${total_automated}</h3><div>Total Automated</div></div>
  <div class="card"><h3>${total_missing}</h3><div>Total Missing</div></div>
</div>

<div class="top-controls">
  <div class="search-box">
    <input type="text" id="searchInput" onkeyup="searchFeature()" placeholder="Search feature...">
  </div>

  <div class="legend">
      <span><span class="dot green"></span> ≥ 90%</span>
      <span><span class="dot orange"></span> 80–89%</span>
      <span><span class="dot red"></span> < 80%</span>
  </div>
</div>

<div class="table-wrapper">
<table id="coverageTable">
<thead>
<tr>
<th>No.</th>
<th class="sortable" onclick="sortTable(1)"><div><span>Feature</span><span class="arrow"></span></div></th>
<th class="sortable" onclick="sortTable(2)"><div><span>Planned</span><span class="arrow"></span></div></th>
<th class="sortable" onclick="sortTable(3)"><div><span>Automated</span><span class="arrow"></span></div></th>
<th class="sortable" onclick="sortTable(4)">
<div><span>Coverage %</span><span class="arrow"></span></div>
<div class="coverage-filter" onclick="event.stopPropagation();">
<select id="coverageFilter" onchange="filterCoverage()">
<option value="all">All</option>
<option value="green">🟢 ≥ 90%</option>
<option value="orange">🟠 80–89%</option>
<option value="red">🔴 < 80%</option>
<option value="grey">⚪ N/A</option>
</select>
</div>
</th>
<th class="sortable" onclick="sortTable(5)"><div><span>Missing</span><span class="arrow"></span></div></th>
</tr>
</thead>
<tbody>
${table_rows}
${warning_note}
</tbody>
</table>
</div>

</div>
</div>
</body>
</html>
