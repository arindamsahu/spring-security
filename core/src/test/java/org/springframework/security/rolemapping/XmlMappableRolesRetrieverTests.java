package org.springframework.security.rolemapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import junit.framework.TestCase;

/**
 * 
 * @author TSARDD
 * @since 18-okt-2007
 */
public class XmlMappableRolesRetrieverTests extends TestCase {
	private static final String DEFAULT_XML = "<roles><role>Role1</role><role>Role2</role></roles>";

	private static final String DEFAULT_XPATH = "/roles/role/text()";

	private static final String[] DEFAULT_EXPECTED_ROLES = new String[] { "Role1", "Role2" };

	public final void testAfterPropertiesSetException() {
		TestXmlMappableRolesRetriever t = new TestXmlMappableRolesRetriever();
		try {
			t.afterPropertiesSet();
			fail("AfterPropertiesSet didn't throw expected exception");
		} catch (IllegalArgumentException expected) {
		} catch (Exception unexpected) {
			fail("AfterPropertiesSet throws unexpected exception");
		}
	}

	public void testGetMappableRoles() {
		XmlMappableRolesRetriever r = getXmlMappableRolesRetriever(true, getDefaultInputStream(), DEFAULT_XPATH);
		String[] resultRoles = r.getMappableRoles();
		assertNotNull("Result roles should not be null", resultRoles);
		assertTrue("Number of result roles doesn't match expected number of roles", resultRoles.length == DEFAULT_EXPECTED_ROLES.length);
		Collection resultRolesColl = Arrays.asList(resultRoles);
		Collection expectedRolesColl = Arrays.asList(DEFAULT_EXPECTED_ROLES);
		assertTrue("Role collections do not match", expectedRolesColl.containsAll(resultRolesColl)
				&& resultRolesColl.containsAll(expectedRolesColl));
	}

	public void testCloseInputStream() {
		testCloseInputStream(true);
	}

	public void testDontCloseInputStream() {
		testCloseInputStream(false);
	}

	private void testCloseInputStream(boolean closeAfterRead) {
		CloseableByteArrayInputStream is = getDefaultInputStream();
		XmlMappableRolesRetriever r = getXmlMappableRolesRetriever(closeAfterRead, is, DEFAULT_XPATH);
		r.getMappableRoles();
		assertEquals(is.isClosed(), closeAfterRead);
	}

	private XmlMappableRolesRetriever getXmlMappableRolesRetriever(boolean closeInputStream, InputStream is, String xpath) {
		XmlMappableRolesRetriever result = new TestXmlMappableRolesRetriever();
		result.setCloseInputStream(closeInputStream);
		result.setXmlInputStream(is);
		result.setXpathExpression(xpath);
		try {
			result.afterPropertiesSet();
		} catch (Exception e) {
			fail("Unexpected exception" + e.toString());
		}
		return result;
	}

	private CloseableByteArrayInputStream getDefaultInputStream() {
		return getInputStream(DEFAULT_XML);
	}

	private CloseableByteArrayInputStream getInputStream(String data) {
		return new CloseableByteArrayInputStream(data.getBytes());
	}

	private static final class TestXmlMappableRolesRetriever extends XmlMappableRolesRetriever {
	}

	private static final class CloseableByteArrayInputStream extends ByteArrayInputStream {
		private boolean closed = false;

		public CloseableByteArrayInputStream(byte[] buf) {
			super(buf);
		}

		public void close() throws IOException {
			super.close();
			closed = true;
		}

		public boolean isClosed() {
			return closed;
		}
	}
}
