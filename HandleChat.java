package chatbot;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.*;
import javax.*;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class HandleChat {
	
	public static void main(String[] args) {

		String chrome_driver_location = "";
		String from_email = "":
		String password = "";
		String to_email = "":

		System.setProperty("webdriver.chrome.driver", chrome_driver_location);
		
		Properties props = new Properties(); 
		
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from_email, password);
				}
		});
		
		try {
			Message message = new MimeMessage(session);
 			message.setFrom(new InternetAddress(from_email));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to_email));

			message.setSubject("kommunicate");
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText("This is message body");
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.addArguments("disable-infobars");
			
			WebDriver driver = new ChromeDriver(options);
			
			driver.get("https://test.kommunicate.io");
			
			@SuppressWarnings("deprecation")
			WebDriverWait wait = new WebDriverWait(driver,30);
			
				    
			Actions act = new Actions(driver);
	    
			act.moveToElement(driver.findElement(By.xpath("//iframe[@id='kommunicate-widget-iframe']"))).perform();
		    	    
			driver.findElement(By.xpath("//iframe[@id='kommunicate-widget-iframe']")).click();
			
			
			driver.switchTo().frame("kommunicate-widget-iframe");
			
			try 
			{	    
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("km-faq")));
			}
			catch(TimeoutException e)
			{
				messageBodyPart1.setText("Could not find FAQ's button");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);
			}
				
			driver.findElement(By.xpath("//button[@id='km-faq']")).click();
						
			int li_size = driver.findElements(By.xpath("//li[@class='km-faq-list']")).size();
			
			if(li_size > 20) {
				
				messageBodyPart1.setText("No.Of FAQ's = "+li_size);			
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);
	 
			}
			
			
		} catch (MessagingException e) {
 
			throw new RuntimeException(e);
 
		}
			
	}
	

}
