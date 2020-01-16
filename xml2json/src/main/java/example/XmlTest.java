package example;

import org.json.JSONException;

/**
 * Created by zhangjianxin on 2017/3/25.
 */

public class XmlTest extends XmlExercise {
	public static String TEST_XML_STRING = "<?xml version=\"1.0\" encoding=\"GBK\" ?><OUTPUT><RETURN><CODE>000000</CODE><MESS>666</MESS></RETURN><DATA><JSPBH>1111111123123123123</JSPBH><NSRSBH>345678901987654323456</NSRSBH><NSRMC>150001192601041093</NSRMC><SWJGDM>123123124125125</SWJGDM><SWJGMC>閲嶅簡甯傚浗瀹剁◣鍔″眬鍒嗗眬</SWJGMC><FXDQBH>11111</FXDQBH><DQSZ>20170324174115</DQSZ><QYSJ>20170321</QYSJ><BBH>DJLASHJKAHL-123123123</BBH><FJH>0</FJH><QYLX>0</QYLX><QTKZXX></QTKZXX><PZSQXX COUNT=\"1\"><PZSQ><FPZL>51</FPZL><SSRQ>20170415</SSRQ><DZKPXE>999999.99</DZKPXE><SCJZRQ>1</SCJZRQ><LXSX>999</LXSX><LXXE>999999999.00</LXXE><LXSYJE>999999999.00</LXSYJE><LXKZXX></LXKZXX><SQSLXX COUNT=\"8\"><SQSL><SL>0.00</SL></SQSL><SQSL><SL>0.03</SL></SQSL><SQSL><SL>0.04</SL></SQSL><SQSL><SL>0.05</SL></SQSL><SQSL><SL>0.11</SL></SQSL><SQSL><SL>0.13</SL></SQSL><SQSL><SL>0.17</SL></SQSL><SQSL><SL>0.06</SL></SQSL></SQSLXX></PZSQ></PZSQXX></DATA></OUTPUT>";

	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n"
				+ "<note>\n" + "<to>George</to>\n" + "<from>John</from>\n"
				+ "<heading>Reminder</heading>\n"
				+ "<body>Don't forget the meeting!</body>\n" + "</note>";
		String str = xml2json(xml);

		System.out.println("to_json" + str);

		com.alibaba.fastjson.JSONObject result = xml2fastjson(TEST_XML_STRING);
		System.out.println("to_xml2fastjson" + result);

	}
}