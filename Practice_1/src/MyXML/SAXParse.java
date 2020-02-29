package MyXML;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParse {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		//SAX����
		//1����ȡ��������
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//2���ӽ���������ȡ������
		SAXParser parser = factory.newSAXParser();

		//4�������ĵ�Document ע�ᴦ����
		PersonHandler handler = new PersonHandler();
		//5������
		parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("MyXML/person.xml"), handler);
		//6����ȡ����
		ArrayList<Person> persons = handler.getPersons();
		System.out.println("size:\t"+persons.size());

		for(Person ps:persons) {
			System.out.println(ps.getName()+"-->"+ps.getAge());
		}
	}
}

//3����д������
class PersonHandler extends DefaultHandler{
	private ArrayList<Person> persons;
	private Person person;
	private String tag;	//��������ı�ǩ��
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("---------Start---------");
		
		this.persons = new ArrayList<Person>();
	}
	
	public ArrayList<Person> getPersons() {
		return persons;
	}

	//qName:	��ǩ��/Ԫ����
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(null!=qName) {
				tag  = qName;
			if(tag.equals("person")) {
				person = new Person();
			}
		}
	}
	

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String context  = new String(ch, start, length).trim();

		if(null!=tag) {
			if(tag.equals("name")) {
				if(context.length()>0) {
					person.setName(context);
				}
			}else if (tag.equals("age")) {
				if(context.length()>0) {
					person.setAge(Integer.valueOf(context));
				}
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		tag = qName;	//��
		if(tag!=null) {
			if(tag.equals("person")) {
				persons.add(person);
			}
		}
		tag = null;			//��
		//������Ϊnull��Ϊ��ʡ��</XXX>����Ŀո��
		//��endElement����Ҫͨ��tag������add��list��
		//��˽�add���̵Ĵ�����ڢٺ͢���
	}
	
	@Override
	public void endDocument() throws SAXException {
		System.out.println("---------End---------");
	}
	
}
