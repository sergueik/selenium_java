/*
 * {{{ header & license
 * Copyright (c) 2016 Farrukh Mirza
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */

/**
 * @author Farrukh Mirza
 * 24/06/2016 
 * Dublin, Ireland
 */
package example.service;

import org.springframework.stereotype.Service;

import example.spi.TestDataProvider;

@Service
public class TestDataProviderImpl implements TestDataProvider {

	@Override
	public String getTestDataObject() {
//		String json = getSimpleJsonData();
		String json = getSimpleJsonDataMultipleSignatures();
		// System.out.println(json);
		return json;
	}

	@Override
	public String getTestDataArray() {
//		String json = getJsonDataArray();
		String json = getJsonDataArrayMultipleSignatures();
		// System.out.println(json);
		return json;
	}

	@Override
	public String getHtmlTemplateDoc() {
//		String html = getSimpleThreateningLetter();
		String html = getSimpleThreateningLetterForArray();
		// System.out.println(html);
		return html;
	}

	@Override
	public String getHtmlDoc() {
		// String html=getSimpleImageUrl();
		String html = getSimpleQuotesWithImageUrl();
		// System.out.println(html);
		return html;
	}

	@Override
	public String getCssDoc() {
		String css = getSimpleCssManual();
		// System.out.println(css);
		return css;
	}

	/*
	 * 
	 * HTML test data
	 * 
	 */

	/*
	 * The image from the image url gets embedded as long as the image path is
	 * publicly available.
	 */
	private String getSimpleImageUrl() {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"<p><img alt=\"\" src=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png\"/></p>");
		return sb.toString();
	}

	private String getSimpleQuotesWithImageUrl() {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"<p><img alt=\"\" src=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png\" /> <h1>January Announcement</h1></p>");
		sb.append(
				"<p>Good morning everyone, just a gentle reminder that we lost everything just like every January.<br/> And now the CEO is going crazy. </p>");
		sb.append("<p><strong>Quotes all around (Gibrish): </strong></p>");
		sb.append(
				"<p><strong>Nina Myers:&nbsp;</strong>He is gonna put a bullet in my head before I can say hello, and then he'll turn the gun on himself. Don&#39;t change any audit numbers even in decimals. Every thing must be accounted for. Not a single dime should be out of place. All sale records <h3>must</h3> be closed by 26th Dec.</p>");
		sb.append(
				"<p>Sarah Conor: Watching John with the machine, it was suddenly so clear. The terminator, would never stop. It would never leave him, and it would never hurt him, never shout at him, or get drunk and hit him, or say it was too busy to spend time with him. It would always be there. And it would die, to protect him. Of all the would-be fathers who came and went over the years, this thing, this machine, was the only one who measured up. In an insane world, it was the sanest choice. </p>");
		sb.append(
				"<p><strong>George Mason:&nbsp;</strong>Believe it or not, I used to want to be a teacher. A long time ago. You know why I didn't? DOD offered me more money. That's how I made my decision. So I made myself miserable. And I made everybody else around me miserable. For an extra five thousand dollars a year. That was my price. </p>");
		sb.append(
				"<p>Tom, don't let anybody kid you. It's all personal, every bit of business. Every piece of shit every man has to eat every day of his life is personal. They call it business. OK. But it's personal as hell. You know where I learned that from? The Don. My old man. The Godfather. If a bolt of lightning hit a friend of his the old man would take it personal. He took my going into the Marines personal. That's what makes him great. The Great Don. He takes everything personal Like God. He knows every feather that falls from the tail of a sparrow or however the hell it goes? Right? And you know something? Accidents don't happen to people who take accidents as a personal insult..</p>");
		sb.append("<p>Never let anyone know what you are thinking.</p>");
		sb.append("<p>Thank you,<br/></p>");
		sb.append("<p>AllCast&nbsp;</p>");
		return sb.toString();
	}

	private String getSimpleSalesLetter() {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"<p>Good morning, Converter is now live. I&#39;m very excited that we have this new service, finally.&nbsp;</p>");
		sb.append(
				"<p>Let&#39;s do what we have done really well this year by getting our converters looking as good as possible. I have emailed head office to enable service in all consumers one by one, not ideal but use this strategy to avoid any hiccups. I know some consumers do not have internet coverage yet, please text me the consumer name and will organise as per priority &nbsp;with boss. Jack is double checking prices on catalogue as some prices are wrong. </p>");
		sb.append(
				"<p>In your email accounts I have also sent you a link of discounted lines with comparitive prices. Only give discounts to valued or returning customers. Some discounts are specifically designed for new customers.</p>");
		sb.append("<p>Call me on phone if you need me.</p>");
		sb.append("<p>Thanks.</p>");
		sb.append("<p>Johnny&nbsp;</p>");
		return sb.toString();
	}

	private String getSimpleThreateningLetter() {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"<p><img alt=\"\" src=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png\" /></p>");
		sb.append("<p>To,</p>");
		sb.append("<p><strong>Mr. {name.first} {name.last}</strong>,</p>");
		sb.append("<p>I hope this letter finds you in good health.</p>");
		sb.append(
				"<p>This letter wishes to bring your attention towards your non-payment of charges associated with using our services. ");
		sb.append("Please go to our website to make a payment. ");
		sb.append("Please be advised that any non-payment can result in serious criminal charges brought forward.</p>");
		sb.append("<p>Yours Truly,</p>");
		sb.append("<p><strong>{signature}</strong></p>");
		sb.append("<p>Customer Relationship Manager</p>");
		sb.append("<p>The core business Inc.</p>");

		return sb.toString();
	}

	private String getSimpleThreateningLetterForArray() {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"<p><img alt=\"\" src=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png\" /></p>");
		sb.append("<p>To,</p>");
		sb.append("<p><strong>Mr. {name.first} {name.last}</strong>,</p>");
		sb.append("<p>I hope this letter finds you in good health.</p>");
		sb.append(
				"<p>This letter wishes to bring your attention towards your non-payment of charges associated with using our services. ");
		sb.append("Please go to our website to make a payment. ");
		sb.append("Please be advised that any non-payment can result in serious criminal charges brought forward.</p>");
		sb.append("<p>Yours Truly,</p>");
		sb.append("<table>");
		sb.append("<tr>");
		sb.append("<repeat>");
		sb.append("<td>");
		sb.append("<p><strong>{signatures[*].name}</strong></p>");
		sb.append("<p><strong>{signatures[*].designation}</strong></p>");
		sb.append("</td>");
		sb.append("</repeat>");
		sb.append("</tr>");
		sb.append("</table>");
//		sb.append("<p><strong>{signature}</strong></p>");
//		sb.append("<p>Customer Relationship Manager</p>");
		sb.append("<p>The core business Inc.</p>");

		return sb.toString();
	}

	/*
	 * Css Test Data
	 */
	private String getSimpleCssManual() {
		StringBuffer sb = new StringBuffer();
		sb.append("h1 {color: blue;text-align: center;}");
		sb.append("strong {color: red;}");
		sb.append("p {font-family: verdana;font-size: 20px;}");

		return sb.toString();
	}

	/*
	 * JSON Test Data
	 */
	private String getSimpleJsonData() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"name\":{");
		sb.append("\"first\":\"Richard\"");
		sb.append(",");
		sb.append("\"last\":\"Roe\"");
		sb.append("}");

		sb.append(",");
		sb.append("\"fullName\":\"Richard Roe\"");
		sb.append(",");
		sb.append("\"signature\":\"Johnny English\"");
		sb.append("}");
		return sb.toString();
	}

	private String getSimpleJsonDataMultipleSignatures() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"name\":{");
		sb.append("\"first\":\"Richard\"");
		sb.append(",");
		sb.append("\"last\":\"Roe\"");
		sb.append("}");

		sb.append(",");
		sb.append("\"fullName\":\"Richard Roe\"");
		sb.append(",");
		sb.append("\"signatures\": [{\"name\":\"Ethan Hunt\", \"designation\":\"Impossible Mission Force\"}, {\"name\":\"Michael Corleone\", \"designation\":\"The Reasoning Department\"}]");
		sb.append("}");
		return sb.toString();
	}

	private String getJsonDataArray() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(
				"{\"name\":{\"first\":\"Nina\",\"last\":\"Myers\"},\"fullName\": \"Nina Myers\",\"signature\": \"Jack Bauer\"}");
		sb.append(",");
		sb.append(
				"{\"name\":{\"first\":\"John\",\"last\":\"Doe\"},\"fullName\": \"John Doe\",\"signature\": \"Jack & Jill\"}");
		sb.append(",");
		sb.append(
				"{\"name\":{\"first\":\"Richard\",\"last\":\"Roe\"},\"fullName\": \"Richard Roe\",\"signature\": \"Jack Bauer\"}");
		sb.append("]");
		return sb.toString();
	}
	
	private String getJsonDataArrayMultipleSignatures() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(
				"{\"name\":{\"first\":\"Nina\",\"last\":\"Myers\"},\"fullName\": \"Nina Myers\",\"signatures\": [{\"name\":\"Jack Bauer\", \"designation\":\"The Boss\"}, {\"name\":\"Jack & Jill\", \"designation\":\"Customer Relationship Manager\"}]}");
		sb.append(",");
		sb.append(
				"{\"name\":{\"first\":\"John\",\"last\":\"Doe\"},\"fullName\": \"John Doe\",\"signatures\": [{\"name\":\"Ethan Hunt\", \"designation\":\"Impossible Mission Force\"}, {\"name\":\"Michael Corleone\", \"designation\":\"The Reasoning Department\"}]}");
		sb.append(",");
		sb.append(
				"{\"name\":{\"first\":\"Richard\",\"last\":\"Roe\"},\"fullName\": \"Richard Roe\",\"signatures\": [{\"name\":\"Fredo Corleone\", \"designation\":\"The Useless Department\"}]}");
		sb.append("]");
		return sb.toString();
	}
}
