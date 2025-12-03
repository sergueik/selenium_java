# 2. ****Two Migration Philosophies****

## ****A. Big-Bang Migration (a.k.a. Full Cutover)****

****Definition:****\
The entire bank switches from the old core to the new core ****on a
single go-live weekend****.\
Everything --- accounts, products, transactions, channels, GL feeds ---
moves at once.

****Mental model:****\
Like switching an airplane's engines mid-flight, but all engines at
once.

### Pros:

- ****Fastest total transformation****
- ****Lower long-term integration costs**** (old system can be shut down
  quickly)
- Clean-cut product behavior: no drift between old and new logic

### Cons:

- ****Highest operational risk****\
  If something fails, everything fails.
- Requires ****massive multi-month "dress rehearsals"****
- Requires ****100% feature readiness**** on Day 1\
  (including obscure corner flows like "reverse interest adjustment on
  dormant accounts")

### Typical Use Cases:

- Very small banks / neobanks with simple product sets
- Banks with aggressive deadlines or regulatory pressure
- When legacy system is failing or support is ending

## ****B. Stepwise / Progressive Migration****

****Definition:****\
Move the bank to the new platform ****in several controlled phases****
--- sometimes one ****product****, ****segment****, ****business
line****, or ****channel**** at a time.

Examples of steps:

- Phase 1: New checking/savings accounts for new customers only
- Phase 2: Migrate existing checking/savings
- Phase 3: Move loans
- Phase 4: Move treasury/cash management
- Phase 5: Retire old general ledger adapters
- Phase 6: Turn off legacy core

****Mental model:****\
Landing the airplane by installing one engine at a time while the others
keep flying.

### Pros:

- ****Lower risk****, because each migration is smaller
- ****Business continuity**** (legacy and new platforms can coexist)
- Can deliver ****value early**** (e.g., modern APIs for new products)
- Allows ****parallel treatment of technical debt****
- Lets bank learn and adjust after each phase

### Cons:

- Requires ****temporary coexistence architecture****
- More complex ****integration layers**** (old core + new core
  simultaneously)
- Requires strict ****product mapping discipline**** to avoid
  inconsistent rules

### Typical Use Cases:

- Medium/large banks with complex product catalogs
- Banks wanting minimal customer disruption
- When channels cannot all be modernized at the same time

# ****What Is Being Migrated? (Core concepts)****

Even experienced IT folks outside banking may not realize how special a
core banking system is. In simplest terms:

  Term                                 Meaning (simple)
  ------------------------------------ ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  ****Core Banking System (Core)****   The bank's transaction-processing engine: deposits, withdrawals, payments, interest, fees, posting, end-of-day ("EOD") runs, general ledger feeds. Think "ERP for money movement."
  ****Products****                     Checking, savings, loans, credit cards, mortgages. Each product family has its own data structures, business rules, and posting logic.
  ****Customer Data****                Identity, addresses, KYC data, account relationships. Often deeply embedded in the old core.
  ****Posting Cycle****                The daily/weekly/monthly batch jobs calculating fees, interest, balances.
  ****Channels****                     Mobile apps, online banking, ATMs, teller systems --- all depend on the core via APIs or message queues.

Migrating a core means moving ****all product data, configs, rules, and
posting logic**** into a new system ****without breaking anything that
touches money****

# ****Term Definitions****

  Term                                       Simple Meaning
  ------------------------------------------ ------------------------------------------------------------------------------------------------------------------
  ****Progressive Modernization****          Modernize piece-by-piece instead of all at once.
  ****Decoupling****                         Building APIs to isolate channels from the old core so they can later redirect to the new one.
  ****Strangler Pattern****                  Gradually replace old functionality by routing new flows to the modern system, "strangling" the old.
  ****Dual Posting / Parallel Posting****    Temporarily write each transaction to both old and new systems to verify correctness.
  ****Dual Run****                           Operate both cores side-by-side for a period, checking that balances match.
  ****Product Migration Wave****             A batch of accounts/products moved together during a controlled window.
  ****Event Gateway / Middleware Layer****   A translation layer enabling old channels to talk to new core via adapters until channel modernization is ready.

# ****How Banks Decide: Stepwise vs Big-Bang****

## ****Factor 1 --- Complexity of Existing Products****

- ****Simple product set**** → big-bang possible
- ****Customized/legacy products**** → stepwise preferred

## ****Factor 2 --- Regression Risk****

- A bank with 40+ years of accreted business rules generally cannot
  fully test every corner case for a one-weekend cutover.

## ****Factor 3 --- Tolerance for Customer Disruption****

- Big-bang creates a ****single high-risk event****
- Stepwise spreads smaller risks over time

## ****Factor 4 --- Integration Architecture****

If channels are already API-based → stepwise is easier.\
If channels are tightly coupled to the legacy core → big-bang may become
the only viable option.

## ****Factor 5 --- Business Roadmap****

- Banks often want ****new products**** early
- Stepwise migration lets the bank release new features on the new
  platform without waiting for complete cutover

## ****Factor 6 --- Vendor / Core Platform Capability****

Some vendors (including FIS) ****explicitly design**** for progressive
migration --- with product-level onboarding, dual posting, and
coexistence middleware.

## Public / Shareable References for Migration Strategies

  Source / Paper                                                                                                       What it Covers                                                                                                                                                                                                                                                                                                                                                                                              Why Useful
  -------------------------------------------------------------------------------------------------------------------- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  **FIS --- "Targeted Core Banking Modernization" White Paper**                                                        Argues for a "component-based / incremental modernization" (versus full core rip-and-replace). Describes a "low-risk incremental approach" via modular components rather than monolithic replacement. [FIS Global+1](https://www.fisglobal.com/-/media/fisglobal/files/pdf/white-paper/core-modernization-white-paper.pdf?utm_source=chatgpt.com)                                                           Great for discussions with executives/architects: explains why incremental modernization is often more viable than big-bang for large, complex banks.
  **10x Banking + AWS / Contino --- "Making sense of cloud-native core banking migration" (2023)**                     Explains a six-step framework for migrating to a modern, cloud-native core while reducing risk; emphasizes phased / "coexistence" strategies over full cutover. [10xbanking.com+1](https://www.10xbanking.com/downloads/core-banking-data-migration?utm_source=chatgpt.com)                                                                                                                                 Useful to show a modern, real-world minded, cloud-native migration approach --- good selling material for converting legacy cores in conservative banking IT environments.
  Analyst / Strategy Report --- McKinsey & Company "Core banking migration during M&A: Seven keys to success" (2023)   Though oriented toward M&A, covers many of the same migration-risk factors; recommends careful planning, strong business sponsorship, and sometimes phased implementation when "single cutover" risk is too high. [McKinsey & Company](https://www.mckinsey.com/industries/financial-services/our-insights/banking-matters/core-banking-migration-during-ma-seven-keys-to-success?utm_source=chatgpt.com)   Good for showing that even in high-pressure scenarios (M&A), a phased or hybrid migration strategy is often more predictable and safer than full cutover.
  Industry Guide --- Virtusa "From Legacy to Digital Leadership" (2025)                                                Provides a roadmap for moving from monolithic legacy cores to agile, cloud-native or modular platforms; discusses trade-offs between re-architect, re-platform, hybrid, or replace models. [Virtusa](https://www.virtusa.com/insights/whitepaper/from-legacy-to-digital-leadership?utm_source=chatgpt.com)                                                                                                  Helps frame the decision criteria and strategic context --- useful when aligning business / technology leadership on what "modernization" really means.
  Comparative Overview --- "Core Banking Modernisation: A Complete Guide" (third-party fintech consultancy / blog)     Lays out modernization drivers (open banking, APIs, customer expectations), challenges (data, integration, legacy), and strategic choices including phased migration vs full core replacement. [Meniga+2Whatfix+2](https://www.meniga.com/resources/core-banking-modernisation/?utm_source=chatgpt.com)                                                                                                     Good layman-friendly reference that non-technical stakeholders (product, compliance, risk) can digest, to build shared understanding of why modernization is needed and what the choices are.
