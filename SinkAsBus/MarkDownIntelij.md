# Markdown | IntelliJ IDEA Documentation

<!-- TOC -->
* [Markdown | IntelliJ IDEA Documentation](#markdown--intellij-idea-documentation)
  * [IntelliJ IDEA 2024.3](#intellij-idea-20243)
    * [Shortcuts: Windows](#shortcuts-windows)
  * [Get IntelliJ IDEA](#get-intellij-idea)
  * [Markdown](#markdown)
    * [Last modified: 22 November 2024](#last-modified-22-november-2024)
    * [Configure:](#configure)
    * [Filename patterns:](#filename-patterns)
    * [Enable the Markdown plugin](#enable-the-markdown-plugin)
    * [Create a new Markdown file](#create-a-new-markdown-file)
    * [Code blocks](#code-blocks)
      * [Disable coding assistance in code blocks](#disable-coding-assistance-in-code-blocks)
    * [Run commands from Markdown files](#run-commands-from-markdown-files)
    * [Diagrams](#diagrams)
      * [Enable Mermaid diagram support](#enable-mermaid-diagram-support)
      * [Enable PlantUML diagram support](#enable-plantuml-diagram-support)
    * [HTML preview](#html-preview)
    * [Split editor and preview horizontally](#split-editor-and-preview-horizontally)
    * [Disable editor and preview scrollbar synchronization](#disable-editor-and-preview-scrollbar-synchronization)
    * [Change preview font size](#change-preview-font-size)
    * [Custom CSS](#custom-css)
      * [Configure](#configure-1)
<!-- TOC -->

## IntelliJ IDEA 2024.3

### Shortcuts: Windows

- **Shortcuts**: Windows

## Get IntelliJ IDEA

- **Getting started**
- **IDE configuration**
- **Project configuration**
- **Search everywhere**
- **Write and edit source code**
- **Compilation and building**
- **Running**
- **Debugging**
- **Deployment**
- **Testing**
- **Analysis**
- **Version control**
- **AI Assistant**
- **Remote development**
- **Dev Containers**
- **Integrated tools**
- **Web development**
- **Database Tools and SQL**
- **JVM frameworks**
- **Kotlin**
- **Scala**
- **Groovy**
- **Non-JVM technologies**
    - **Dart**
    - **Go**
    - **JSON**
    - **Liquid**
    - **Markdown**
    - **Markdown language settings**
    - **Markdown code style settings**
    - **PHP**
    - **Python**
    - **Ruby**
    - **Rust**
    - **Shell scripts**
    - **Terraform**
    - **XML**
    - **XPath and XSLT**
    - **YAML**
    - **Reference**
    - **Non-JVM technologies**

## Markdown

### Last modified: 22 November 2024

### Configure:
- **Settings** | **Languages & Frameworks** | **Markdown**

### Filename patterns:
- `*.md` and `*.markdown`

Markdown is a lightweight markup language for adding formatting elements to plain text. IntelliJ IDEA recognizes Markdown files, provides a dedicated editor with highlighting, completion, and formatting, and shows the rendered HTML in a live preview pane. Support is based on the [CommonMark specification](https://commonmark.org/).

### Enable the Markdown plugin

This functionality relies on the Markdown plugin, which is bundled and enabled in IntelliJ IDEA by default. If the relevant features are not available, make sure that you did not disable the plugin.

1. Press `Ctrl + Alt + S` to open settings and then select `Plugins`.
2. Open the `Installed` tab, find the `Markdown` plugin, and select the checkbox next to the plugin name.

### Create a new Markdown file

By default, IntelliJ IDEA recognizes any file with the `.md` or `.markdown` extension as a Markdown file.

1. Right-click a directory in the Project tool window (`Alt + 1`) and select `New | File`.
2. Alternatively, you can select the necessary directory, press `Alt + Insert`, and then select `File`.
3. Enter a name for your file with a recognized extension, for example: `readme.md`.

The Markdown editor provides several basic formatting actions in the floating toolbar that appears when you select a text fragment. You can use the preview pane to see the rendered HTML. There is also completion for links to files in the current project.

### Code blocks

To insert a fenced code block, use triple backticks (\`\`\`) before and after the code block. If you specify the language for the code block, by default, the Markdown editor injects the corresponding language. This enables syntax highlighting and other coding assistance features for the specified language: completion, inspections, and intention actions.

#### Disable coding assistance in code blocks

If your code blocks are not meant to be syntactically correct, you may want to disable code injection and syntax errors in code blocks.

1. Press `Ctrl + Alt + S` to open settings and then select `Languages & Frameworks | Markdown`.
2. Clear the following options:
    - `Inject languages in code fences`
    - `Show problems in code fences`
3. Click `OK` to apply the changes.

### Run commands from Markdown files

When you clone a project, there is usually a `README.md` file with instructions and commands to run the application, configure your environment, and so on. IntelliJ IDEA detects these commands and provides gutter icons for running the commands. Click the corresponding gutter icon or press `Ctrl + Shift + F10` while the caret is at the command that you want to run.

You can disable the gutter icons for running commands in Markdown files in IDE settings (`Ctrl + Alt + S`) under `Languages & Frameworks | Markdown`: clear the `Detect commands that can be run right from Markdown files` checkbox.

### Diagrams

The Markdown editor can render diagrams defined with Mermaid and PlantUML. This is disabled by default and requires additional steps.

#### Enable Mermaid diagram support

1. Press `Ctrl + Alt + S` to open settings and then select `Plugins`.
2. Find and install the `Mermaid` plugin.

#### Enable PlantUML diagram support

1. Press `Ctrl + Alt + S` to open settings and then select `Languages & Frameworks | Markdown`.
2. Install and enable `PlantUML` under `Markdown Extensions`.
3. After IntelliJ IDEA downloads the relevant extension, click `OK` to apply the changes.

### HTML preview

> **Note**: The preview does not work on Android Studio. This is a known issue and there is a workaround: use JetBrains Runtime instead of the default Android Studio runtime.

By default, the Markdown editor shows a preview pane for rendered HTML code next to the Markdown source. You can click `Editor` or `Preview` in the top right corner of the Markdown editor to show only the editor or the preview pane.

### Split editor and preview horizontally

By default, the editor and the preview are split vertically (side by side), which is convenient for wide monitors. You can also split it horizontally, so that the preview is displayed in the lower part of the editor, which is more convenient for portrait displays.

1. In the top-right corner of the editor, click to open the `Editor Preview` pane.
2. Click to split the editor and the preview horizontally.
3. To configure the default layout of the preview, you can use the `Preview layout` list in `Languages & Frameworks | Markdown`.

### Disable editor and preview scrollbar synchronization

By default, the scrollbars in the editor and in the preview pane are synchronized, meaning that the location in the preview pane corresponds to the location in the source.

1. Press `Ctrl + Alt + S` to open settings and then select `Languages & Frameworks | Markdown`.
2. Clear `Sync scroll in the editor and preview`.
3. Click `OK` to apply the changes.

### Change preview font size

Although you can define the font size for the preview by customizing the CSS, it is possible to set the font size for the built-in style sheets.

1. Press `Ctrl + Alt + S` to open settings and then select `Languages & Frameworks | Markdown`.
2. Set the font size in the `Preview font size` field.
3. Click `OK` to apply the changes.

If you often need to adjust the preview font size, instead of changing it in the settings, assign shortcuts to the following two actions: `Increase Preview Font Size` and `Decrease Preview Font Size`.

### Custom CSS

IntelliJ IDEA provides default style sheets for rendering HTML in the preview pane. These style sheets were designed to be consistent with the default UI themes. You can configure specific CSS rules to make small presentation changes: for example, change the font size for headings or line spacing in lists. Or you can provide an entirely new CSS to better match your expected output: for example, if you want to replicate the GitHub Markdown style.

#### Configure