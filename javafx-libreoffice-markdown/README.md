__LibreOffice__ *is* actively developing direct support for importing and exporting Markdown files. While this feature is not yet fully implemented and available in current stable releases, it is expected to be included in future versions, specifically with the __26.2__ release scheduled for next year.

NOTE: there is currenrtly *no* release tagged __26__ even in [daily builds](https://dev-builds.libreoffice.org/daily/). The not so near future releases are likely developed in `libreoffice-7-x` branches

Current Limitations and Workarounds:

* No native direct export in current stable versions: __LibreOffice__ __Writer__ does not natively support exporting directly to `.md` files in its current stable releases.

__Pandoc__ a [universal document convertor](https://pandoc.org/installing.html) can be used to convert __LibreOffice__ documents (`.odt`) into various __Markdown__ formats. Pandoc is a Haskell library for converting from one markup format to another, and a command line tool that launches that library

```cmd
c:\Users\kouzm\AppData\Local\Pandoc\pandoc.exe -f odt -t Markdown  -o Memo_Posting.md  Memo_Posting.odt

```
```sh
sed -i 's|^#|###|g'  Memo_Posting.md
sed -i 's|\*\*\*\*\(.*\)\*\*\*\*|\1|g' Memo_Posting.md
clip < Memo_Posting.md
```
Third-party extensions or scripts: Some users might find or develop extensions or scripts to facilitate Markdown conversion, though these are not officially supported features.

Future Prospects:

Upcoming native support: Developers are working on adding robust Markdown import and export capabilities to __LibreOffice__, including support for basic elements like **paragraphs**, **headings**, and **lists**.

Improved workflow: Once fully implemented, this will allow for a more seamless workflow for users who work with __Markdown__ documents within __LibreOffice__.


### See Also

   * https://nibblestew.blogspot.com/2022/09/looking-at-libreoffices-windows.html
   * __Markdig__- Markdown processor for .NET. - [repo](https://github.com/xoofx/markdig) and [artifact](https://www.nuget.org/packages/Markdig)

### Author

[Serguei Kouzmine](mailto:kouzmine_serguei@yahoo.com)
