package in.focalworks.spring;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

@Service
public class MessageBean {

	public String getMessage() {
		return "Button was clicked at " + LocalTime.now();
	}
}
