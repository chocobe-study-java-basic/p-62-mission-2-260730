package com.github.chocobe.sbb_mission2.global.markdown;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component
public class MarkdownUtil {

    private Parser  parser = Parser.builder().build();
    private HtmlRenderer renderer = HtmlRenderer.builder().build();

    public String parseToHtml(String markdown) {
        Node document = this.parser.parse(markdown);
        return this.renderer.render(document);
    }

}
