### Info

Classic [RGM](https://en.wikipedia.org/wiki/Rube_Goldberg_machine) [RPA](https://en.wikipedia.org/wiki/Robotic_process_automation):

![Rube Goldberg chain reaction machine pinup pop art retro PNG illustration. Comic book style imitation](screenshots/flow.jpg)

A mechanism that accomplishes a trivial end result by means of an extended chain of loosely related, finish-to-finish obscure interactions, each activating the next in a cascading "domino effect"

Classic __Rude Golberg__ *teller screens*:

* Candle burning a rope
* Rope releases a weight
* Weight tips a seesaw
* Seesaw knocks a domino
* Domino toppling occurs, with each piece in turn knocking over the next
* Needle hits a balloon
* Scissors cut a rope
* Balloon rises and pulls a string
* String lifts a lever → pours tea

![Rube Goldberg chain reaction machine pinup pop art retro PNG illustration. Comic book style imitation](screenshots/flow.svg)
### Author

[Serguei Kouzmine](mailto:kouzmine_serguei@yahoo.com)


### Related Info

DVI (Device Independent File Format) and PostScript (PS) within the context of TeX and printing technology. That’s a fantastic analogy to highlight the complexity of a mainframe to API-first cloud migration!

Let me unpack what you're saying and align it with the mainframe-to-cloud migration idea:

DVI vs PostScript (PS)

DVI (Device Independent Format):

Purpose: DVI was designed to be a device-independent output format for TeX, meaning it was created to represent the output of TeX without making any assumptions about the final output device (e.g., printer, screen, etc.). The focus was on compactness, where the primary goal was to produce a minimalist, raw format that could be interpreted and rendered by various devices, but without making the format too verbose or device-specific.

Characteristics:

Very simple and efficient: It just holds the raw information about how text and figures should be placed on a page.

Lacks higher-level constructs for things like fonts or color management, leaving much of that interpretation to the downstream rendering process (like a printer or display device).

Compact, but cryptic: It doesn't aim to describe everything in a human-readable way or optimize for quality printing directly.

PostScript (PS):

Purpose: PostScript is a page description language that was designed with a very different goal in mind. It was meant for high-quality print output, particularly for low-capacity devices like printers, and it provided much more sophisticated ways to describe pages (fonts, graphics, color management, etc.). PS allows for fine-grained control over how the final output should look, and it's inherently device-dependent (i.e., it describes the output as commands to a device that understands PostScript).

Characteristics:

Pre-computed coordinates: PostScript programs typically contain highly detailed instructions about exactly where to place things on the page, often describing everything in terms of absolute coordinates, making the final output highly refined and device-specific.

More verbose and complex: Unlike DVI, PostScript is rich and detailed, capturing a lot of context about fonts, page layout, and printing specifics.

Higher quality: PS is designed to ensure high-quality printed output, which is why it’s cryptic in the sense that it’s a specialized language built for a specific purpose.


Connecting DVI and PS to Mainframe-to-Cloud API Migration

In your analogy, I think you're pointing out the fundamental differences in design goals and usage between DVI and PS, and then comparing that to the shift from mainframe-based, monolithic systems to cloud-based, API-first architectures.

Device Independence (DVI) vs Device-Specific (PostScript):

Mainframe systems are like PostScript: They are device-specific, meaning they were built for a particular environment, with tight coupling to hardware, operating systems, and often proprietary software. Mainframe systems are optimized for high reliability but lack flexibility and scalability.

API-first cloud solutions are more like DVI: They are device-independent in the sense that cloud architectures and APIs are designed to be flexible, working across many different environments (whether that’s microservices, containers, or different cloud providers). The key difference, of course, is that APIs are designed to enable dynamic, scalable interactions, whereas DVI was more about a minimal, "compact" output format.

Complexity of PostScript vs Simplicity of DVI:

Mainframe-to-cloud migration isn’t a simple, minimal change (like moving from DVI to another device-independent format). It’s more akin to the complexity of moving from PostScript to a flexible, modular cloud architecture where everything needs to be re-imagined, re-structured, and possibly rebuilt from the ground up.

Mainframe systems often deal with complex legacy processes, and moving to API-first cloud solutions requires translating these legacy processes into modular components that can be easily exposed via APIs. This is very different from simply taking the old format and "moving" it as-is.

Overlapping Goals and Technologies:

With PostScript, the high-level goal was quality output (high-res printing) that was optimized for specific devices (printers). In the mainframe scenario, the goal is often to keep the same business logic or functional capabilities while transforming the underlying infrastructure to be more scalable and adaptable in the cloud.

With DVI, you’re more concerned with just getting the data in a minimal, device-independent format. In a mainframe-to-API migration, you'd be rethinking the data handling, transforming monolithic business processes into cloud-native designs, which often involves much more than simply "migrating" data or functionality.

The Full Analogous Shift (Reverse DVI to PS):

So, just as moving from DVI to PostScript is a full transformation (not just a reformatting), migrating from mainframe to cloud (API-first) is a complete reconstruction of the system from the ground up. In both cases, you are moving away from legacy systems that were designed for different purposes and environments, toward a more flexible, scalable, and modernized solution. There is no direct overlap, and the new system is entirely different in how it operates, just like how DVI (compact and simple) is fundamentally different from PostScript (rich and detailed).




![jenkins job XML](screenshots/jenkins_job.xml.jpg)
Jenkins Job

![blue prism](screenshots/blue_prism.jpg)
Blue Prism Process

![uipath Main XAML](screenshots/uipath_main.jpg)
UIPath FlowChart

![uipath IDE](screenshots/uipath_ui.jpg)
UIPath Designer

![Visual Studio Workflow IDE](screenshots/wwf.jpg)
Visual Studio 2010 Workflow Designer

![Mule Step Flow](screenshots/mule.jpg)
Mule Strp Flow

Despite XAML’s infamous reputation as part of Microsoft’s largely failed ‘Internet reinvention’ during the Vista era and its practical dormancy for decades thereafter, UiPath adopted it as the foundation for workflow serialization
