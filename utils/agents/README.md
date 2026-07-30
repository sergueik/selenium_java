### Info

The *GitHub Skills collection* refers to a rapidly growing ecosystem
of open-source "agent skills"—modular, installable files (often .skill or .md) that
enhance the capabilities of AI coding tools like Claude Code, Cursor, and GitHub Copilot

The best repository for GitHub Copilot agent skills collections on GitHub is the official [github/awesome-copilot](https://github.com/github/awesome-copilot) repository,
(a.k.a.  https://awesome-copilot.github.com/skills/) which serves as the gold standard for verified, community-driven `SKILL.md` collections,
and also misc. tools, and modular agent prompts. As of mid-2026, 

an 12,232 public repositories matching the [agent-skills topic](https://github.com/topics/agent-skills)

A typical skill collection repository may have thousands of entries:
```sh

git clone https://github.com/sickn33/agentic-awesome-skills/
ls -1 agentic-awesome-skills/skills |wc -l
```
```text
1903
```
or compact
```sh
curl -skL -s https://github.com/sickn33/agentic-awesome-skills/archive/refs/heads/main.tar.gz | tar -tzf - | cut -d/ -f2-3  | sort -u| grep '^skills/[^/]*$'  | wc -l
```
```text
1905
```
or
```
curl -s https://api.github.com/repos/sickn33/agentic-awesome-skills/git/trees/main?recursive=1 | jq -r '.tree[].path' | grep '^skills/[^/]\+$' | wc -l
```
```
1904
```

GitHub search supports combining topics and search terms. For example:
```sh
```

which may be done with `gh` of plain GitHub REST API:
```
curl -s 'https://api.github.com/search/repositories?q=topic:agent-skills+spring-batch'
```

however one has to be careful :

```sh
curl -s 'https://api.github.com/search/repositories?q=topic:agent-skills+spring-batch' | jq '.'
```
```js
{
  "total_count": 0,
  "incomplete_results": false,
  "items": []
}

```
GitHub currently indexes repositories and source code separately. While repositories can be discovered by topic (e.g., agent-skills), there is no REST API query that directly searches for a skill inside all repositories having a given topic. A practical solution is therefore a two-stage search (repository discovery followed by code search), or maintaining a dedicated skill index.

one can sort and limit:

```
curl -s 'https://api.github.com/search/repositories?q=topic:agent-skills&sort=stars&order=desc&per_page=20' | jq -r '.items[].full_name' | while read repo; do     echo "Searching $repo..." ; done
```
```sh
COUNT=50
curl -s "https://api.github.com/search/repositories?q=topic:agent-skills&sort=stars&order=desc&per_page=$COUNT" | jq -r '.items[].full_name' | while read repo; do     echo -e "\t$repo" ; done
```

```text
	anthropics/skills
	
	
	VoltAgent/awesome-openclaw-skills
	CherryHQ/cherry-studio
	sickn33/agentic-awesome-skills
	wshobson/agents
	github/awesome-copilot
	K-Dense-AI/scientific-agent-skills
	blader/humanizer
	
	OthmanAdi/planning-with-files
	phuryn/pm-skills
	
	alirezarezvani/claude-skills
	
	kubesphere/kubesphere
	alibaba/zvec
	
	
	nexu-io/html-anything
	tt-a1i/archify
	AgriciDaniel/claude-ad
	refly-ai/refly
	jnMetaCode/superpowers-zh
	Agents365-ai/drawio-skill
	htmlstreamofficial/preline
	
	ThinkInAIXYZ/deepchat
	
	breaking-brake/cc-wf-studio
	gosom/google-maps-scraper
	
	tinyplex/tinybase
	0xNyk/awesome-hermes-agent
	
	

```


```
https://github.com/github/awesome-copilot/tree/main/skills/create-spring-boot-java-project
https://github.com/github/awesome-copilot/tree/main/skills/java-springboot
https://github.com/github/awesome-copilot/tree/main/skills/spring-boot-testing
https://github.com/jdubois/dr-jskill/blob/main/SKILL.md
https://github.com/anthropics/skills/tree/main/skills/webapp-testing

https://github.com/Amplicode/spring-skills/blob/main/skills/spring-data-jpa/SKILL.md
https://github.com/Amplicode/spring-skills/blob/main/skills/dto-creator/
https://github.com/Amplicode/spring-skills/tree/main/skills/spring-security-configuration
https://github.com/Amplicode/spring-skills/tree/main/skills
https://github.com/alirezarezvani/claude-skills/tree/main/markdown-html/skills
https://github.com/spring-projects/spring-batch/blob/main/spring-batch-samples/README.md
https://github.com/spring-projects/spring-batch/blob/main/spring-batch-samples/README.md
```
```sh
./find-skill.sh --repo sickn33/agentic-awesome-skills --skill java-pro
```
```text
sickn33/agentic-awesome-skills/skills/java-pro
```
```sh
./find-skill.sh --repo sickn33/agentic-awesome-skills --skill java-pro -h
```
```text
Usage:
  ./find-skill.sh -r owner/repository -s skill-name

Options:
  -r, --repo      GitHub repository (owner/name)
  -s, --skill     Skill directory name to find
  -h, --help      Show this help

Example:
  ./find-skill.sh -r sickn33/agentic-awesome-skills -s java-pro
will output:

https://github.com/sickn33/agentic-awesome-skills/tree/main/skills/java-pro
```
```
./find-skill.sh  -r JetBrains/skills -s spring -p .
```
```
https://github.com/JetBrains/skills/tree/main/jpa-spring-data-kotlin-mapper
https://github.com/JetBrains/skills/tree/main/kotlin-idiomatic-refactorer-spring-aware
https://github.com/JetBrains/skills/tree/main/kotlin-spring-proxy-compatibility
https://github.com/JetBrains/skills/tree/main/spring-context-di-reasoning
https://github.com/JetBrains/skills/tree/main/spring-kotlin-code-review
https://github.com/JetBrains/skills/tree/main/spring-mvc-webflux-api-builder
https://github.com/JetBrains/skills/tree/main/spring-security-configurator-auditor
```

alternatively 
```powershell
. .\find-skill.ps1
```
```text
Usage:
  ./find-skill.ps1 -Repo owner/repository -Skill skill-name

Options:
  -Repo        GitHub repository (owner/name)
  -Skill       Skill directory name to find
  -Help        Show this help
  -Debug       Show diagnostic information

Example:
  ./find-skill.ps1
      -Repo sickn33/agentic-awesome-skills
      -Skill java-pro
```
```powershell
. .\find-skill.ps1 -repo "sickn33/agentic-awesome-skills" -skill "java-pro"
```
```text
https://github.com/sickn33/agentic-awesome-skills/tree/main/skills/java-pro
```
```powershell
. .\find-skill.ps1 -repo "sickn33/agentic-awesome-skills" -skill  "java-pr" -debug
```
```text
Repository: sickn33/agentic-awesome-skills
Skill:      java-pr
API URL:    https://api.github.com/repos/sickn33/agentic-awesome-skills/contents/skills
Calling GitHub API...
Received 1000 entries
https://github.com/sickn33/agentic-awesome-skills/tree/main/skills/java-pro
```

```powershell
@('anthropics/skills', 'DietrichGebert/ponytail', 'nexu-io/open-design', 'addyosmani/agent-skills', 'ComposioHQ/awesome-claude-skills', 'VoltAgent/awesome-openclaw-skills', 'hesreallyhim/awesome-claude-code', 'CherryHQ/cherry-studio', 'sickn33/agentic-awesome-skills', 'wshobson/agents', 'github/awesome-copilot', 'K-Dense-AI/scientific-agent-skills', 'blader/humanizer', 'googleworkspace/cli', 'topoteretes/cognee', 'VoltAgent/awesome-agent-skills', 'OthmanAdi/planning-with-files', 'phuryn/pm-skills', 'JimLiu/baoyu-skills', 'agentskills/agentskills') | foreach-object { . .\find-skill.ps1 -repo $_ -skill 'java' -ErrorAction SilentlyContinue }
```

```text
https://github.com/github/awesome-copilot/tree/main/skills/create-spring-boot-java-project
https://github.com/github/awesome-copilot/tree/main/skills/java-add-graalvm-native-image-support
https://github.com/github/awesome-copilot/tree/main/skills/java-docs
https://github.com/github/awesome-copilot/tree/main/skills/java-helidon
https://github.com/github/awesome-copilot/tree/main/skills/java-junit
https://github.com/github/awesome-copilot/tree/main/skills/java-mcp-server-generator
https://github.com/github/awesome-copilot/tree/main/skills/java-refactoring-extract-method
https://github.com/github/awesome-copilot/tree/main/skills/java-refactoring-remove-parameter
https://github.com/github/awesome-copilot/tree/main/skills/java-springboot
https://github.com/github/awesome-copilot/tree/main/skills/javascript-typescript-jest
https://github.com/github/awesome-copilot/tree/main/skills/javax-to-jakarta-migration
https://github.com/github/awesome-copilot/tree/main/skills/create-spring-boot-java-project
https://github.com/github/awesome-copilot/tree/main/skills/create-spring-boot-kotlin-project
https://github.com/github/awesome-copilot/tree/main/skills/java-springboot
https://github.com/github/awesome-copilot/tree/main/skills/kotlin-springboot
https://github.com/github/awesome-copilot/tree/main/skills/spring-boot-testing
```

There are also still curated classifier skill repositories repositories like https://github.com/VoltAgent/awesome-agent-skills 
containing focused links e.g.


https://github.com/redis/agent-skills/tree/main/skills


### TL,DR;

Over roughly the last year, several related ideas have converged:
| observe            | remark                                      |
|--------------------|---------------------------------------------|
|Agent Skills        | reusable bundles of instructions, workflows, and supporting files that an AI agent can invoke for specific tasks. These became prominent through GitHub Copilot, Anthropic projects, community skill libraries, and similar ecosystems|
| Precision prompting | moving away from long conversational prompts toward well-structured, deterministic prompts with explicit goals, constraints, examples, and success criteria |
| Prompt engineering becoming software engineering | prompts increasingly live in version control, are tested, reviewed, and treated as project assets rather than ad hoc text|
| Tool-augmented agents | agents that can call tools, search, execute code, access MCP servers, or interact with APIs instead of relying only on the LLM's internal knowledge|
|Reusable context | project memories, repositories of conventions, style guides, and organization-specific knowledge replacing repeated prompt text|
|Structured outputs |JSON schemas, typed outputs, N8N, Mermaid, and predictable interfaces replacing free-form natural language where automation is involved|
|MCP and agent protocols| moving AI capability out of a single IDE into a standardized ecosystem where agents can discover and use tools, services, repositories, and enterprise systems independently of the editor|

One could summarize the trend as:

The shift from *"prompting an LLM"* to *"engineering AI agents with reusable skills, tools, and structured context."*

Or even more succinctly:

*"The industrialization of prompt engineering"*

For an article or presentation, you could reasonably write something like:

One of the defining AI engineering trends of the past year has been the emergence of reusable agent skills, precision prompting, and tool-integrated workflows. Rather than relying on increasingly elaborate ad hoc prompts, developers are encapsulating domain knowledge, project conventions, and best practices into version-controlled, reusable assets that enable more reliable and maintainable AI-assisted development.

That captures the broader movement without overstating the novelty of any single component.

> The romantic era of prompting is ending. The future is not the perfect prompt, but the engineered agent: skills, tools, context, and workflows replacing improvisation with repeatable intelligence

> Prompting is entering its post-romantic era. Like chess after the age of immortal games, AI work is moving from individual brilliance toward engineered systems where every move can be prepared, reproduced, and improved.


High risk appetite — accepting material imbalance and uncertainty.
Preference for initiative — speed, development, king attacks, forcing moves.
Gambits — giving material to gain time, activity, or psychological pressure.
Aesthetic priorities — beauty, originality, and brilliance mattered.
Less emphasis on long-term positional accumulation — at least compared with later classical schools.

But, as you point out, the "they underestimated strategy" narrative is too shallow. They underestimated the size of the strategic domain. Many ideas that seemed like "mere calculation" or "attacker's intuition" were later understood as deeper positional concepts:

initiative is a strategic asset;
development advantage is a positional advantage;
king safety is not just an attacking theme but a long-term structural factor;
space, activity, and coordination can compensate for material.

The Romantic players were exploring a part of the chess state space that later theory formalized.

That maps surprisingly well to early AI prompting.

The "prompt romantic" might be characterized as:

Romantic prompting trait	Later interpretation
Clever wording tricks	Early exploration of the interface between human intent and model behavior
Huge creative prompts	Discovery of latent capabilities
"Prompt hacks"	Prototype versions of workflow engineering
Personal intuition	Informal domain expertise
Improvised chains of reasoning	Early agent workflows

omantic prompting → engineered agents as a historical transition
Skills/context/tools/protocols as the equivalent of chess theory, opening books, and positional understanding
The shift from individual prompt brilliance to reproducible systems
MCP and agent protocols as infrastructure rather than a replacement for the IDE
The caution that early "romantic" practitioners explored real territory, even if the model was incomplete
### 
It has the same "romantic" flavor because it emphasizes compression without loss of expressive power.

The idea is that languages like Mermaid, Graphviz DOT, PlantUML, and workflow DSLs are not merely concise—they deliberately eliminate incidental detail so that the remaining words carry more semantic weight.

The same principle should not be misunderstood as a rejection of more verbose enterprise automation formats. Counterparts such as Jenkins Jelly, Workflow Foundation XAML, UiPath workflow artifacts, Blue Prism process definitions, and BPM-oriented IDE representations may appear heavy compared with the elegance of Graphviz DOT or Mermaid. Yet their verbosity is not accidental noise; it is often an implementation detail carrying metadata, tooling contracts, versioning information, execution semantics, and enterprise governance requirements. Their role is closer to XML in the software ecosystem: not replaced by the elegance of YAML or JSON, but continuing to exist because different layers require different forms of expression.

Or, with the romantic metaphor preserved:

Not every language seeks to be poetry. Some are closer to engineering blueprints: verbose by necessity, rich in annotations, and designed for machines, auditors, and enterprise lifecycles. Their beauty lies not in brevity, but in faithfully carrying complexity.


### Not Yet

Until very recently there was no equivalent of awesome-copilot dedicated to COBOL/JCL/CICS/IMS/DB2/PEGA that has achieved broad recognition across the mainframe community.

Where the momentum actually is

The strongest ecosystem today was around [IBM Z Open Editor](https://github.com/IBM/zopeneditor-about)(
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

https://github.com/Amplicode/spring-skills/tree/main/skills/
# Plan for Building the Spring Batch Skill

## Goal

Create a **project-specific Skill** that teaches both humans and LLMs how to
understand, extend, and maintain this Spring Batch application. The Skill should
capture not only APIs but also architecture, conventions, workflows, and
engineering practices.

---

## Phase 1 — Inventory the Project

Document the application's major capabilities.

Topics to identify:

- Spring Batch version
- Spring Boot version
- Chunk processing
- Tasklets
- Parallel execution
- Partitioning
- JobRepository
- Restartability
- ExecutionContext
- Scheduling
- Large file processing
- REST API
- WebSocket progress reporting
- Browser UI
- Metrics
- Testing
- Deployment model

Deliverable:
A concise architecture inventory.

---

## Phase 2 — Collect Authoritative References

Rank references by authority.

Primary

- Spring Batch Reference Documentation
- Official Spring Batch Samples
  https://github.com/spring-projects/spring-batch/tree/main/spring-batch-samples
- Spring Batch migration guides

Secondary

- Project source code
- https://github.com/javacodingskills/SpringBatch
- Production blog posts
- Frequently cited StackOverflow discussions

Goal:
Prefer official patterns whenever examples disagree.

---

## Phase 3 — Reverse Engineer Project Patterns

Identify recurring implementation patterns.

Examples

- Job classes
- Step definitions
- Reader implementations
- Processor implementations
- Writer implementations
- Tasklets
- Listeners
- Configuration classes
- REST controllers
- WebSocket handlers
- DTOs
- Utility classes

Deliverable:
A catalog of reusable project conventions.

---

## Phase 4 — Separate Concepts from APIs

Teach responsibilities instead of syntax.

Examples

Job
- orchestrates work

Step
- performs work

Reader
- acquires data

Processor
- transforms data

Writer
- persists data

Tasklet
- performs imperative operations

Listeners
- observe execution

---

## Phase 5 — Build the Mental Model

Describe the overall execution flow.

Example

User

↓

REST API

↓

Launch Job

↓

Job

↓

Step

↓

Reader

↓

Processor

↓

Writer

↓

Commit

↓

Progress Event

↓

WebSocket

↓

Browser UI

Deliverable:
A high-level execution model.

---

## Phase 6 — Capture Project Conventions

Examples

- Constructor injection only
- Readers contain no business logic
- Processors are stateless
- Writers own persistence
- Every Step is restartable
- Chunk size is configurable
- ExecutionContext stores restart state
- Progress events are asynchronous
- UI never polls the database directly
- Every Job has integration tests

Deliverable:
Coding conventions for the project.

---

## Phase 7 — Create a Recipe Catalog

Index common engineering tasks.

Examples

- Import CSV
- Import XML
- Import JSON
- Process ZIP archives
- Database-to-database jobs
- Multi-file reader
- Parallel processing
- Partitioned jobs
- Restart failed job
- Resume checkpoint
- Tasklet implementation
- Progress reporting
- WebSocket monitoring

Each recipe should reference the best example.

---

## Phase 8 — Record Anti-Patterns

Document mistakes to avoid.

Examples

- Business logic inside Reader
- Database updates inside Processor
- Mutable singleton state
- Static caches
- Ignoring restartability
- Bypassing JobRepository
- Missing transaction boundaries
- Blocking WebSocket threads
- UI coupled directly to batch logic

Deliverable:
Common pitfalls section.

---

## Phase 9 — Organize the Skill as a Knowledge Base

Suggested layout

skills/
└── spring-batch/
    ├── SKILL.md
    ├── architecture.md
    ├── batch-lifecycle.md
    ├── chunk-processing.md
    ├── tasklets.md
    ├── readers.md
    ├── processors.md
    ├── writers.md
    ├── restartability.md
    ├── partitioning.md
    ├── scaling.md
    ├── websocket-progress.md
    ├── frontend.md
    ├── testing.md
    ├── conventions.md
    ├── pitfalls.md
    └── recipes.md

SKILL.md should remain concise and point to these deeper documents.

---

## Phase 10 — Build a Review Checklist

When modifying code, the Skill should guide the agent through questions such as:

1. Which Job is affected?
2. Which Step is affected?
3. Chunk or Tasklet?
4. Is restartability preserved?
5. Are transaction boundaries correct?
6. Reader responsibilities respected?
7. Processor responsibilities respected?
8. Writer responsibilities respected?
9. Parallel execution still safe?
10. Progress reporting updated?
11. WebSocket notifications correct?
12. UI impact considered?
13. Tests updated?
14. Does the solution follow project conventions?

Deliverable:
A repeatable engineering review process.

---

## Phase 11 — Future Enhancements

Possible specialized sub-skills

- Spring Batch Testing
- Spring Batch Scaling
- Spring Batch Performance Tuning
- Spring Batch Monitoring
- Spring Batch WebSocket Dashboard
- Spring Batch Migration
- Spring Batch Operational Runbook

---

## Final Objective

Produce a Skill that teaches:

- architecture
- conventions
- engineering methodology
- implementation patterns
- operational practices
- project-specific knowledge

rather than serving as another API reference.

The finished Skill should help an LLM think like an experienced Spring Batch
developer working on this specific project.

### See Also
  * [softaworks/agent-toolkit](https://github.com/softaworks/agent-toolkit)
  * https://github.com/JetBrains/skills
  * https://awesome-copilot.github.com/skills/ a.k.a. https://github.com/github/awesome-copilot
  * [anthropics/skills](https://github.com/anthropics/skills/tree/main/skills)
  * https://github.com/affaan-m/ECC
  * https://www.linkedin.com/posts/aagupta_a-developer-on-github-just-built-a-full-development-share-7440035210542575616-nKr1/

  * https://github.com/jkordick/wad-ghcp-cobol/tree/main/.github
  * [modernizing legacy code with GitHub Copilot: Tips and examples](https://github.blog/ai-and-ml/github-copilot/modernizing-legacy-code-with-github-copilot-tips-and-examples/)
  * [COBOL developmen with GitHub Copilot](https://pub.towardsai.net/cobol-development-with-github-copilot-fbb916d60d45)
  * https://github.com/pegasystems/pega-launchpad-agent-skills
  * https://github.com/perinbaraj/cobol-app-modernization/tree/main/.github/skills/copybook-mapper
  * https://raw.githubusercontent.com/aiskillstore/marketplace/refs/heads/main/skills/0xdarkmatter/mcp-patterns/SKILL.md
  * https://github.com/microsoft/skills/blob/main/.github/skills/mcp-builder/SKILL.md
  * https://github.com/microsoft/skills/tree/main/.github/plugins/azure-sdk-python/skills/fastapi-router-py
  * https://github.com/microsoft/skills/blob/main/.github/skills/mcp-builder/SKILL.md
  * [spring-projects/spring-batch](https://github.com/spring-projects/spring-batch/blob/main/spring-batch-samples/README.md)  - Spring Batch Samples
  * [MCP Server GitHub Skills](https://mcpservers.org/agent-skills/author/github)
  * [javacodingskills/SpringBatch](https://github.com/javacodingskills/SpringBatch) - All the spring batch related code base
  * [majiayu000/claude-skill-registry](https://github.com/majiayu000/claude-skill-registry) - he most comprehensive Claude Code skills registry | Web Search: - note massive 
  * https://github.com/majiayu000/claude-skill-registry/tree/main/skills
