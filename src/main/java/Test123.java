import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

public class Test123 {

	@Test
	public void loadAll_Users() throws Exception {
	User user1 = new User(1, "John", "Doe");
	User user2 = new User(1, "John", "Doe");
	//ReflectionAssert.assertReflectionEquals(user1, user2);
	org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(user1, user2);
	}
}
