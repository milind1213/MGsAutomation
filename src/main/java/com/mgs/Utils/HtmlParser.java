package com.mgs.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.Assert;

import java.io.*;
import java.util.List;

public class HtmlParser {
    /**
     * Parse(File in, String charsetName, String baseUri) method loads and parses a HTML file.
     *
     * @param filePath         Absolute html path
     * @param encodingStandard encoding used in the html file . ex: UTF-8
     * @param baseUrl          provide base url, or "" empty string if not
     * @return Document
     */
    public Document parseHtml(String filePath, String encodingStandard, String baseUrl) {
        try {
            Document doc = Jsoup.parse(new File(filePath), encodingStandard, baseUrl);
            return doc;
        } catch (IOException e) {
            Assert.fail("Failed to parse html provided in location " + filePath);
            return null;
        }
    }

    /**
     * Parse method parses the input HTML into a new Document
     *
     * @param html
     * @return Document
     */
    public Document parseHtml(String html) {
        Document doc = Jsoup.parse(html);
        return doc;
    }

    /**
     * Parse method parses the input HTML into a new Document.
     *
     * @param html    HTML which needs to be parsed
     * @param baseUrl provide base url, or "" empty string if not (which makes it work similar to parseHtml(String html)
     * @return Document
     */
    public Document parseHtml(String html, String baseUrl) {
        Document doc = Jsoup.parse(html, baseUrl);
        return doc;
    }

    /**
     * parseHtmlBodyFragment method creates an empty shell document,
     * and inserts the parsed HTML into the body element. If you used the normal
     * Jsoup.parse(String html) method, you would generally get the same result, but explicitly treating the input as
     * a body fragment ensures that any bozo HTML provided by the user is parsed into the body element.
     *
     * @param html
     * @return Document
     */
    public Document parseHtmlBodyFragment(String html) {
        Document doc = Jsoup.parseBodyFragment(html);
        return doc;
    }

    /**
     * creates a new Connection, and get() fetches and parses a HTML file.
     *
     * @param url html file url
     * @return Document
     */
    public Document parseHtmlFromURL(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Extract specific attribute from element
     *
     * @param element element object whose specific attribute has to be retrieved
     * @param key     attribute key which has to be retrieved from the element
     * @return specific attribute of element
     */
    public String extractAttributeFromElement(Element element, String key) {
        return element.attr(key);
    }

    /**
     * Extract text from element
     *
     * @param element element object whose text has to be retrieved
     * @return element text
     */
    public String extractTextFromElement(Element element) {
        return element.text();
    }

    /**
     * Extract inner html from element
     *
     * @param element element object whose inner html has to be retrieved
     * @return inner html
     */
    public String extractInnerHtmlFromElement(Element element) {
        return element.html();
    }

    /**
     * Extract outer html from element
     *
     * @param element element object whose outer html has to be retrieved
     * @return outer html
     */
    public String extractOuterHtmlFromElement(Element element) {
        return element.outerHtml();
    }

    /**
     * Append html in the selected element
     *
     * @param element         element object where node is selected
     * @param elementLocation element location in the selected node
     * @param htmlOrText      HTML/Text which needs to be appended
     */
    public void appendHtmlOrTextOfElement(Element element, String elementLocation, String htmlOrText) {
        element.select(elementLocation).append(htmlOrText);
    }

    /**
     * Creates an element with the specified tag name in the current document
     *
     * @param document            document object of the html being parsed
     * @param tagNameOfNewElement tag name of the new element being created
     */
    public void createHtmlElement(Document document, String tagNameOfNewElement) {
        document.createElement(tagNameOfNewElement);
    }

    /**
     * Prepend html in the selected element
     *
     * @param element           element object where node is selected
     * @param cssLocatorAddress css locator in the selected node
     * @param htmlOrText        HTML/Text which needs to be prepended
     */
    public void prependHtmlOrTextOfElement(Element element, String cssLocatorAddress, String htmlOrText) {
        element.select(cssLocatorAddress).prepend(htmlOrText);
    }

    /**
     * Remove html element by css locator
     *
     * @param document          document object of the html being parsed
     * @param cssLocatorAddress css locator address of the element to be removed
     * @param index             index of element to be removed
     */
    public void removeHtmlElementByCss(Document document, String cssLocatorAddress, int index) {
        document.select(cssLocatorAddress).remove(index);
    }

    public void removeHtmlElementsByCss(Document document, String cssLocatorAddress) {
        document.select(cssLocatorAddress).remove();
    }

    /**
     * Get the element object by id attribute
     *
     * @param document document object of the html being parsed
     * @param id       id of the element to be retrieved
     * @return element object
     */
    public Element getHtmlElementById(Document document, String id) {
        return document.getElementById(id);
    }

    /**
     * Get the element object by classname attribute
     *
     * @param document  document object of the html being parsed
     * @param className classname of the element to be retrieved
     * @param index     index of the element to be retrieved (if the element is unique, provide 0 as index, otherwise provide proper index)
     * @return element object
     */
    public Element getHtmlElementByClass(Document document, String className, int index) {
        return document.getElementsByClass(className).get(index);
    }

    /**
     * Get the list of elements object by tag name
     *
     * @param document document object of the html being parsed
     * @param tagName  tag name of the elements to be retrieved
     * @return list of element objects
     */
    public List<Element> getHtmlElementByTag(Document document, String tagName) {
        return document.getElementsByTag(tagName);
    }

    /**
     * Remove similar html elements attribute by classname
     *
     * @param document          document object of the html being parsed
     * @param cssLocatorAddress css locator of the similar elements attribute to be removed
     * @param attribute         attribute name of the similar elements
     */
    public void removeHtmlElementsAttributeByCss(Document document, String cssLocatorAddress, String attribute) {
        document.select(cssLocatorAddress).removeAttr(attribute);
    }

    /**
     * Remove similar html elements attribute by classname
     *
     * @param document          document object of the html being parsed
     * @param cssLocatorAddress css locator of the element attribute to be removed
     * @param attribute         attribute name of the similar elements
     */
    public void removeHtmlElementAttributeByCss(Document document, String cssLocatorAddress, String attribute, int index) {
        document.select(cssLocatorAddress).remove(index).removeAttr(attribute);
    }

    /**
     * Get the Html element by Css locator
     *
     * @param document          document object of the html being parsed
     * @param cssLocatorAddress css locator of the element attribute to be removed
     * @param index             index of the locator, if no index provide 0
     * @return element object
     */
    public Element getHtmlElementByCss(Document document, String cssLocatorAddress, int index) {
        return document.select(cssLocatorAddress).get(index);
    }

    /**
     * Replace inner html text of specified element
     *
     * @param document          document object of the html being parsed
     * @param cssLocatorAddress css locator of the element attribute to be removed
     * @param index             index of the locator, if no index provide 0
     * @param newText           new text to be replaced with the existing inner text
     * @return element object
     */
    public Element replaceTextInHtml(Document document, String cssLocatorAddress, int index, String newText) {
        return document.select(cssLocatorAddress).get(index).text(newText);
    }

    public static String extractText(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(reader);
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String textOnly = Jsoup.parse(sb.toString()).text();
        return textOnly;
    }
}