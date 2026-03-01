import os
import requests

# GitHub environment variables
repo = os.getenv("GITHUB_REPOSITORY")
pr_number = os.getenv("PR_NUMBER")
token = os.getenv("GITHUB_TOKEN")
run_id = os.getenv("GITHUB_RUN_ID")

if not pr_number:
    print("Not a pull request workflow, skipping PR comment")
    exit(0)

with open("artifacts/feature-coverage.md") as f:
    table = f.read()

# Link to workflow run (since GitHub doesn't expose artifact file URL directly)
html_report_link = f"https://github.com/{repo}/actions/runs/{run_id}"

comment_body = f"""
{table}

**HTML Report:**  
[Download Automation Test Coverage Report (artifact)]({html_report_link})
"""

url = f"https://api.github.com/repos/{repo}/issues/{pr_number}/comments"

headers = {
    "Authorization": f"Bearer {token}",
    "Accept": "application/vnd.github+json"
}

response = requests.post(url, headers=headers, json={"body": comment_body})

if response.status_code != 201:
    print("Failed to post PR comment:", response.text)
    exit(1)

print("Feature coverage comment successfully posted to the PR.")