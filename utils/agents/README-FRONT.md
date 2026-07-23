# Repository Instructions and Agent Skills

## Background

The emerging **Agent Skills ecosystem** represents a growing collection of reusable, repository-level guidance files that enhance AI-assisted development tools such as GitHub Copilot, Claude Code, Cursor, and other compatible environments.

These skills are typically lightweight, version-controlled artifacts—often written as Markdown files—that provide context, conventions, workflows, and domain-specific guidance to AI assistants working with a project.

The ecosystem is still evolving, but it follows a familiar engineering pattern: capturing valuable project knowledge in a form that can be shared, reviewed, versioned, and improved over time.

Current public examples are concentrated primarily around technologies such as:

* Java
* TypeScript
* Python
* DevOps
* Kubernetes
* Terraform
* GitHub Actions
* Docker

For some time, there appeared to be fewer publicly available examples targeting enterprise legacy environments such as COBOL, PEGA, and other specialized domains. This landscape is changing as vendors and the community begin publishing reusable skills for these areas.

Examples include:

* https://github.com/pegasystems/pega-launchpad-agent-skills
* https://github.com/perinbaraj/cobol-app-modernization/tree/main/.github/skills/copybook-mapper

---

## Why Repository-Level Guidance Matters

Over the years, successful software repositories have accumulated many small but valuable configuration artifacts:

* `.gitignore`
* `CODEOWNERS`
* CI/CD workflows (`ci.yml`, `build.yml`, etc.)
* lint and formatter configuration (`.editorconfig`, Checkstyle, ESLint, ktlint, SwiftLint, SpotBugs, and others)
* dependency management configuration
* repository templates

None of these files directly implement business functionality.

Nevertheless, they became standard practice because they improve consistency, reduce onboarding time, capture team conventions, and raise the productivity baseline for everyone working on the project.

Repository-level AI guidance appears to be a natural extension of this same idea.

Modern AI development tools can use repository instructions and reusable Agent Skills to better understand project conventions, specialized workflows, and engineering expectations.

These artifacts remain:

* version controlled,
* reviewed through normal engineering processes,
* maintained alongside the source code,
* portable across compatible AI tools.

From a project template perspective, adding these files is inexpensive. Once present, every new repository can start with a baseline of institutional knowledge already captured.

This is especially valuable in a polyglot enterprise environment where projects may contain Java, Kotlin, Swift, Objective-C, PowerShell, Bash, Batch, Terraform/HCL, and other technologies.

The same approach naturally extends to specialized enterprise languages and DSLs such as COBOL, JCL, COPYBOOKs, and other mainframe assets.

---

## Capturing Engineering Practices, Not Just Code Generation

The purpose of repository instructions and Agent Skills is not simply to generate more code.

The more important opportunity is to capture engineering practices that teams have learned over time.

Imagine that, in addition to battle-tested repository artifacts such as `.gitignore`, `CODEOWNERS`, CI workflows, lint rules, and formatter configuration, project templates also include a collection of reusable skills.

The team is not being asked to "just use AI."

Instead, contributors are invited to review these files in the same way they would review any other project configuration.

The feedback naturally comes from domain experts:

**Java developer:**

> "The skills look good, but unit testing is mandatory in our repositories. Please add guidance for mocking frameworks, TDD expectations, and code coverage."

**Terraform engineer:**

> "Infrastructure changes must comply with Sentinel policies. That belongs in the infrastructure skill."

**UX designer:**

> "Before implementing a feature, always start with a simple wireframe or interaction sketch."

**Mainframe specialist:**

> "For COBOL, always verify COPYBOOK compatibility, review JCL before submission, and document expected ABEND handling."

**Security engineer:**

> "Threat modeling and secret scanning should be part of every implementation workflow."

**DBA:**

> "Migration reviews, rollback planning, and data compatibility checks deserve their own skill."

Notice what is not happening.

The discussion is not about AI models, prompt engineering, or the latest tooling trend.

The discussion is about engineering practices that experienced team members already apply—and how to make those practices available to everyone.

The repository becomes a place where organizational knowledge can accumulate.

---

# Beyond Software Engineering

One particularly interesting aspect of repository instructions and Agent Skills is that the underlying concept is not limited to software development.

This should not be surprising. Modern foundation models are designed to work across natural language, programming languages, technical documentation, business writing, and many other forms of structured information.

The same mechanism that helps an AI assistant understand project-specific Java conventions can also help prepare documentation, summarize technical discussions, support design reviews, or adapt communication for different audiences.

The boundary between technical and non-technical work is becoming less rigid.

Consider a common situation experienced by many engineers.

A developer discovers a significant architectural or operational risk. Explaining the issue to another engineer is usually straightforward. Explaining the same risk to a project sponsor or senior executive is more challenging.

Technical accuracy must be preserved, while the message must become concise, business-oriented, and free of unnecessary implementation details.

An engineer can ask an AI assistant:

> "Rewrite this for a non-technical decision maker. Keep it concise, respectful, and factual. Clearly explain the business risk, the likely impact, and why timely action matters."

The engineer remains responsible for the technical assessment and recommendation. The AI assistant helps communicate the information effectively to the intended audience.

The reverse is equally valuable. A business requirement, policy decision, or executive objective can be translated into language that is more actionable for engineers without losing its original intent.

Agent Skills are therefore not only coding accelerators. They are a mechanism for packaging reusable organizational knowledge:

* engineering practices,
* communication patterns,
* documentation standards,
* review checklists,
* operational procedures,
* domain expertise.

In the past, organizations primarily version-controlled their software.

Increasingly, they can also version-control how they communicate, review, explain, and apply their expertise.

---

# Advantages

Consider a new team member joining the project.

Their résumé indicates experience with Java, Terraform, COBOL, or another technology used by the team. As with any new hire, the depth of that experience naturally varies. Some contributors are experts; others have worked with the technology in a different environment or with different engineering practices.

Without repository guidance, onboarding often begins with uncertainty.

Team conventions, architectural patterns, review expectations, and domain-specific practices must be discovered through documentation, trial and error, or repeated review comments.

Repository Instructions and Agent Skills help reduce this learning curve.

When a developer asks an AI coding assistant to implement or modify a feature, the assistant can take the repository's guidance into account.

Depending on the task, this may include:

* generating unit tests,
* following coding standards,
* respecting infrastructure policies,
* suggesting design artifacts such as simple wireframes,
* applying project-specific guidance for COBOL, JCL, COPYBOOKs, Terraform, and other technologies.

Even when a recommendation is unfamiliar, it exposes the developer to the team's preferred practices at the moment they are most relevant.

Instead of learning every convention only through review feedback, contributors receive practical guidance throughout their normal workflow.

Repository Instructions and Agent Skills complement—not replace—engineering judgment.

Senior engineers continue to review designs, mentor team members, explain architectural trade-offs, and make technical decisions.

The goal is to reduce repetitive guidance so that reviews can focus on architecture, correctness, maintainability, security, and business value.

Like any other engineering artifact, these skills are expected to evolve.

As projects mature, technologies change, and new lessons are learned, repository guidance should be updated through the normal pull request and review process.

Over time, the repository becomes more than a source code repository—it becomes a living engineering knowledge base.

Valuable practices are captured once, version-controlled alongside the code, reviewed like any other engineering artifact, and made available to every current and future contributor.

This collection intentionally starts as an evolving foundation assembled from multiple public sources. Some overlap is expected. The structure and content will continue to improve as the team gains practical experience using these capabilities.

The goal is not to provide a finished solution, but to establish a useful foundation that the team can refine and extend together.

---

## See Also

### General Agent Skill Collections

* https://github.com/softaworks/agent-toolkit
* https://github.com/JetBrains/skills
* https://awesome-copilot.github.com/skills/
  (https://github.com/github/awesome-copilot)

### Enterprise and Legacy Domain Examples

* https://github.com/pegasystems/pega-launchpad-agent-skills
* https://github.com/perinbaraj/cobol-app-modernization/tree/main/.github/skills/copybook-mapper
* https://github.com/jkordick/wad-ghcp-cobol/tree/main/.github

### Additional References

* https://mcpmarket.com/tools/skills/python-backend-developer
* https://github.blog/ai-and-ml/github-copilot/modernizing-legacy-code-with-github-copilot-tips-and-examples/
