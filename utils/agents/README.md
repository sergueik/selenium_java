### Info
The *GitHub Skills collection* refers to a rapidly growing ecosystem
of open-source "agent skills"—modular, installable files (often .skill or .md) that
enhance the capabilities of AI coding tools like Claude Code, Cursor, and GitHub Copilot

### See Also
  * [softaworks/agent-toolkit](https://github.com/softaworks/agent-toolkit)
  * https://github.com/JetBrains/skills
  * https://awesome-copilot.github.com/skills/ a.k.a. https://github.com/github/awesome-copilot
  * https://github.com/affaan-m/ECC
  * https://www.linkedin.com/posts/aagupta_a-developer-on-github-just-built-a-full-development-share-7440035210542575616-nKr1/

  * https://github.com/jkordick/wad-ghcp-cobol/tree/main/.github
  * [modernizing legacy code with GitHub Copilot: Tips and examples](https://github.blog/ai-and-ml/github-copilot/modernizing-legacy-code-with-github-copilot-tips-and-examples/)
  * [COBOL developmen with GitHub Copilot](https://pub.towardsai.net/cobol-development-with-github-copilot-fbb916d60d45)
  * https://github.com/pegasystems/pega-launchpad-agent-skills
  * https://github.com/perinbaraj/cobol-app-modernization/tree/main/.github/skills/copybook-mapper
### Not Yet

There is currently no equivalent of awesome-copilot dedicated to COBOL/JCL/CICS/IMS/DB2/PEGA that has achieved broad recognition across the mainframe community.

Where the momentum actually is

The strongest ecosystem today is around [IBM Z Open Editor](https://github.com/IBM/zopeneditor-about)(
Visual Studio Code extension that provides language support for the IBM® Enterprise COBOL, PL/I, HLASM, REXX, and (all new!) JCL languages
) rather than AI skills.



IBM already ships:

  * COBOL
  * PL/I
  * JCL
  * HLASM
  * REXX
  * CICS awareness
  * DB2 SQL awareness
  * Copybook resolution
  * Hundreds of code snippets
  * Language Server Protocol support
  * Zowe integration
  * Job submission from VS Code

It is far beyond syntax highlighting; it is essentially the modern IDE for z/OS development.

AI skills specifically

The majority of public AI skill repositories are overwhelmingly aimed at

  * Java
  * TypeScript
  * Python
  * DevOps
  * Kubernetes
  * Terraform
  * GitHub Actions
  * Docker

rather than juicy legacy enterprise systems.

That isn't because COBOL developers aren't interested—it is largely because much of the domain
knowledge lives behind corporate firewalls jar.

Why there isn't a large public COBOL skill collection

Mainframe work tends to be organization-specific.

A useful "COBOL skill" usually needs knowledge such as

  * local naming conventions
  * installation standards
  * JCL procedures
  * JES2/JES3 configuration
  * RACF conventions
  * DB2 subsystem names
  * CICS transaction naming
  * IMS regions
  * internal copybooks
  * corporate coding standards

Those would not be published openly.

Consequently, companies tend to build skills privately rather than contribute to public repositories.

The PEGA is even more closed

PEGA is largely driven by

  * proprietary documentation
  * internal project conventions
  * generated artifacts

There is essentially no well-known public repository of PEGA AI skills comparable to awesome-copilot.

What does exist

Instead of "skills", you'll find mature knowledge bases:

IBM Redbooks
IBM Samples
Zowe samples
COBOL coding standards
Enterprise code templates
IBM Z Open Editor snippets
internal company playbooks

Those are actually excellent raw material for converting into AI skills.

What one expect to happen in the next 1–2 years
 many organizations creating private enterprise skill packs, for example:


enterprise-mainframe/

    cobol-style-guide.skill
    jcl-review.skill
    cics-debug.skill
    db2-sql.skill
    ims-call.skill
    copybook-conventions.skill
    batch-performance.skill
    abend-triage.skill
    changeman.skill
    endevor.skill

These would encode decades of institutional knowledge that today exists only in senior engineers' heads.

Would this convince an "old timer"?

Ironically, yes—if the skills capture their own expertise.

A COBOL veteran is unlikely to be impressed by a generic "write COBOL" prompt. But they may immediately see value in a skill that understands things like:

"Review this JCL for production readiness."
"Explain why this job may produce an S0C7."
"Check whether VSAM file allocation matches the IDCAMS DEFINE."
"Find missing COMMIT logic in a CICS transaction."
"Suggest DB2 bind options."
"Detect dangerous ALTER statements."
"Review copybook compatibility across COBOL versions."
"Verify Endevor package requirements."

Those are precisely the kinds of repetitive expert tasks where an AI skill can save time without replacing the engineer.

In other words, I think the opportunity is not another generic "awesome skills" repository, but an "Awesome Mainframe Skills" collection.

Such a repository could organize reusable skills by domain:

  * COBOL
  * Enterprise COBOL 6.x
  * JCL
  * JES2/JES3
  * CICS
  * IMS
  * DB2 for z/OS
  * VSAM
  * IDCAMSa
  * DFSORT/SYNCSORT
  * REXX
  * RACF
  * Endevor
  * ChangeMan
  * CA7/Control-M scheduling
  * MQ
  * PEGA
  * Natural/Adabas

Surprisingly, despite the rapid growth of public AI skill ecosystems,
there does not appear to be a widely recognized GitHub repository dedicated to these enterprise legacy domains yet.


### See Also

  * https://github.com/pegasystems/pega-launchpad-agent-skills
  * https://github.com/perinbaraj/cobol-app-modernization/tree/main/.github/skills/copybook-mapper


### The new team member

Now consider a new team member joining the project.

Their résumé says they know Java, Terraform, COBOL, or another technology used by the team.
As with any new hire, the depth of that experience will naturally vary.
Some may be experts; others may have only worked with the technology in a different environment or on a much smaller scale.

Without repository guidance, onboarding often begins with uncertainty.
Team conventions, architectural patterns, review expectations, and domain-specific practices
must all be discovered through documentation, trial and error, or repeated code review comments.

With repository instructions and Agent Skills already present, the experience changes.

When the developer asks the coding agent to implement a feature,
the agent automatically incorporates the project's established practices: generating unit tests,
following coding standards, respecting infrastructure policies,
suggesting wireframes where appropriate,
or applying organization-specific guidance
for COBOL, JCL, COPYBOOKs, Terraform, or other technologies.

The new developer may not yet understand every recommendation,
but they are continuously exposed to the team's preferred practices.
Instead of being intimidated by an unfamiliar technology stack,
they receive practical guidance exactly when it is needed.

In this sense, Agent Skills are not merely a productivity tool—they are an onboarding accelerator.
They encourage engineers to grow into the team's way of working rather than forcing them to learn
every convention the hard way before they can contribute confidently.

Senior engineers still review the work, mentor the developer, and explain the reasoning behind important decisions.

However, many routine expectations and the team's hard-earned lessons are already embedded
- and will continue to be refined — in the repository's Agent Skills area.
This allows code reviews to focus on architecture, design, correctness, and business value instead of
repeatedly correcting the same procedural issues.

In this way, the repository evolves into a living engineering knowledge base.
As new lessons are learned, they are captured once, version-controlled alongside the code,
reviewed like any other engineering artifact, and immediately made available to every current and future contributor.
### See Also

  * https://github.com/pegasystems/pega-launchpad-agent-skills
  * https://github.com/perinbaraj/cobol-app-modernization/tree/main/.github/skills/copybook-mapper
  * [COBOL Development with GitHub Copilot](https://pub.towardsai.net/cobol-development-with-github-copilot-fbb916d60d45)

Proposal: Treat AI repository configuration as a first-class project artifact

Over the years, our repositories have accumulated a number of "small" configuration files that have proven to be surprisingly valuable:

  * `.gitignore`
  * `CODEOWNERS`
  * CI/CD workflows: `ci.yml`, `build.yml`, etc.
  * lint and formatter configuration (target language specific: `.editorconfig`, `Checkstyle`, `ESLint`, `ktlint`, `SwiftLint`, `SpotBugs`, etc.)
  * dependency management
  * repository templates

None of these files directly contribute to business functionality. Nevertheless, they have become standard practice because they improve consistency, reduce onboarding time, capture team conventions, and raise the productivity floor for everyone on the team.

Repository-level AI configuration appears to be the next natural evolution.

GitHub now supports repository instructions and reusable Agent Skills that can live alongside the source code. These are version-controlled artifacts that teach coding agents about our project's conventions and specialized workflows, while remaining portable across multiple compatible AI tools.

From a template perspective, adding these files to new project skeletons is inexpensive. Once present, every repository starts with the same institutional knowledge already encoded.

This is particularly attractive for our polyglot environment, where projects may contain Java, Kotlin, Swift, Objective-C, PowerShell, Bash, Batch, Terraform/HCL, and other technologies. The same approach naturally extends to enterprise-specific languages and DSLs such as COBOL, JCL, COPYBOOKs, and other mainframe assets.

Recent public examples show that this is no longer limited to mainstream languages. Vendors and the community are already publishing reusable skills for enterprise domains, including official Pega Launchpad Agent Skills and COBOL modernization skills such as Copybook Mapper. These suggest that domain expertise can increasingly be packaged as reusable repository assets rather than remaining tribal knowledge.

Rather than viewing these files as "AI prompts," it may be more useful to think of them as another category of project metadata—similar to CODEOWNERS, CI workflows, or lint configuration—that documents and automates how we expect work to be performed.

### Thought experiment: reviewing a new project skeleton
Imagine that, in addition to the **battlefield-proven** project artifacts (`.gitignore`, `CODEOWNERS`, CI workflows, lint configuration, formatter rules, etc.), every repository template also contains repository instructions and a small collection of Agent Skills.

The team is **not** asked to "just use AI." (A slight parody of the classic "just add water" marketing slogan.)

Instead, they are simply asked to review these files in exactly the same way they would review any other project configuration.

The feedback is immediate, inexpensive, and comes naturally from each domain expert:

* **The Java developer says:**

  > "The skills look good, but in our repositories unit testing is not optional. Please add guidance for mocking frameworks, TDD expectations, and code coverage."

* **The Terraform engineer says:**

  > "Infrastructure changes **must** comply with Sentinel policies. That belongs in the infrastructure skill."

* **The UX designer says:**

  > "Before implementing a feature, always start with a simple wireframe or interaction sketch."

* **The mainframe specialist says:**

  > "For COBOL, always verify COPYBOOK compatibility, review JCL before submission, and document the expected ABEND handling."

* **The security engineer says:**

  > "Threat modeling and secret scanning should be part of every implementation workflow."

* **The DBA says:**

  > "Migration reviews, rollback planning, and data compatibility checks deserve their own skill."

Notice what is *not* happening.

Nobody is debating AI models, prompt engineering, or the latest tooling.
The discussion is entirely about engineering practices that the team has learned—sometimes the hard way—and now wants every future contributor to inherit automatically.

The conversation is remarkably similar to the one many organizations had years ago when deciding what belongs in `.gitignore`, `CODEOWNERS`, CI workflows, or linting rules. The only difference is that today's repository can also capture procedural engineering knowledge as reusable, version-controlled skills.



None of these comments require writing application code. They simply capture practices that experienced engineers already apply instinctively.

Once incorporated, these skills become part of the project template.


### Beyond software engineering

One particularly interesting aspect of repository instructions and Agent Skills is that the underlying concept is **not limited to software development**.

This should not be surprising. Modern foundation models are trained to reason across natural language, 
technical documentation, programming languages, business writing, and many other forms of human knowledge. 
As a result, the same mechanism that helps an agent generate better Java code can also help it produce clearer documentation, 
prepare a design review, summarize a technical discussion, or adapt a message for a different audience.

The boundary between technical and non-technical work is therefore becoming less rigid.

Consider a situation that many experienced engineers have encountered.

A developer discovers a significant architectural or operational risk. 
Explaining the issue to another engineer is straightforward. 
Explaining the same risk to a project sponsor or senior executive is much harder. 
Technical accuracy must be preserved, while the language must become concise, 
business-oriented, and free of unnecessary jargon.

Historically, this communication often relied on the management chain to translate technical concerns into business language.

Today, the engineer can ask an AI assistant:

> "Rewrite this for a non-technical decision maker. Keep it concise, respectful, and factual. Clearly explain the business risk, the likely impact, and why timely action matters without relying on implementation details."

The engineer remains fully responsible for the technical content and the recommendation. The AI simply assists in communicating the message effectively to its intended audience.

The reverse is equally valuable. A business requirement, policy change, or executive decision can be translated into language that is actionable for engineers without losing its original intent.

This illustrates a broader point. Agent Skills are not merely coding accelerators—they are reusable organizational knowledge. 
They can encode engineering practices, communication patterns, review checklists, documentation standards, operational procedures, and many other forms of institutional experience that benefit every contributor.


> In the past, organizations primarily version-controlled their software. Increasingly, they can also version-control how they communicate, review, explain, and apply their expertise.
> In other words, we're no longer version-controlling only source code and build logic—we're beginning to version-control engineering know-how itself.

> Foundation models are general-purpose language reasoning engines. Software development is simply one application domain. Therefore, the same mechanisms used to capture engineering expertise can also capture communication practices, documentation standards, architectural review processes, project management workflows, and many other forms of organizational knowledge.


Now consider a new team member joining the project.

They may not yet understand *why* every instruction exists. They may simply ask the coding agent to implement a feature, and the agent will automatically follow the repository's conventions: writing tests, respecting infrastructure policies, suggesting wireframes where appropriate, following mainframe review practices, and applying organization-specific standards when relevant.

The new developer still learns over time, but from day one they benefit from the accumulated experience of the team. The senior engineers are no longer required to repeat the same review comments on every pull request because many of those expectations have already been encoded as reusable project assets.

This is analogous to what happened with `.gitignore`, `CODEOWNERS`, CI workflows, and lint configuration. These files do not replace engineering judgment, but they establish a consistent baseline, reduce repetitive review feedback, and make good practices easier to follow. Repository instructions and Agent Skills extend that idea by capturing procedural knowledge in a form that AI-assisted development tools can understand and apply automatically.

Viewed this way, repository instructions and Agent Skills are not "AI prompts." They are another category of version-controlled engineering metadata that documents and distributes institutional knowledge across the team.



The objective is not to automate expertise, 
but to make expertise reusable. 
Just as we version-control build rules and coding standards, 
we can now version-control recurring engineering guidance
 so that every contributor—human or 
 AI-assisted—starts from the same playbook.


### The new team member

Now consider a new team member joining the project.

Their résumé says they know Java, Terraform, COBOL, or another technology used by the team. 
As with any new hire, the depth of that experience will naturally vary. 
Some may be experts; others may have only worked with the technology in a different environment or on a much smaller scale.

Without repository guidance, onboarding often begins with uncertainty. 
Team conventions, architectural patterns, review expectations, and domain-specific practices 
must all be discovered through documentation, trial and error, or repeated code review comments.

With repository instructions and Agent Skills already present, the experience changes.

When the developer asks the coding agent to implement a feature, 
the agent automatically incorporates the project's established practices: generating unit tests, 
following coding standards, respecting infrastructure policies, 
suggesting wireframes where appropriate, 
or applying organization-specific guidance 
for COBOL, JCL, COPYBOOKs, Terraform, or other technologies.

The new developer may not yet understand every recommendation, 
but they are continuously exposed to the team's preferred practices. 
Instead of being intimidated by an unfamiliar technology stack, 
they receive practical guidance exactly when it is needed. In other words,
The skills don't replace learning; they make learning incremental, contextual, and less intimidating.

In this sense, Agent Skills are not merely a productivity tool—they are an onboarding accelerator. 
They encourage engineers to grow into the team's way of working rather than forcing them to learn 
every convention the hard way before they can contribute confidently.

Senior engineers still review the work, mentor the developer, and explain the reasoning behind important decisions.

However, many routine expectations and the team's hard-earned lessons are already embedded 
- and will continue to be refined — in the repository's Agent Skills area. 
This allows code reviews to focus on architecture, design, correctness, and business value instead of 
repeatedly correcting the same procedural issues.

In this way, the repository evolves into a living engineering knowledge base. 
As new lessons are learned, they are captured once, version-controlled alongside the code, 
reviewed like any other engineering artifact, and immediately made available to every current and future contributor.




The character code for the man facepalming emoji (🤦‍♂️) is a sequence of multiple Unicode code points: U+1F926 (face palm), U+200D (zero width joiner), U+2642 (male sign), and U+FE0F (variation selector-16).

