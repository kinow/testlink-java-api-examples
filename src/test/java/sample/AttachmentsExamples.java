/*
 * The MIT License
 *
 * Copyright (c) <2012> <Bruno P. Kinoshita>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package sample;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.Attachment;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

/**
 * 
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 0.1
 */
public class AttachmentsExamples {

    @Test
    public void testAttachmentsForTestCaseDesign() {
	String url = "http://localhost/testlink-1.9.3/lib/api/xmlrpc.php";
	String devKey = "f7a289d2065ac3ee463a9c0fab5765f2";
	TestLinkAPI api = null;

	URL testlinkURL = null;

	try {
	    testlinkURL = new URL(url);
	} catch (MalformedURLException mue) {
	    mue.printStackTrace(System.err);
	    Assert.fail(mue.getMessage());
	}

	try {
	    api = new TestLinkAPI(testlinkURL, devKey);
	} catch (TestLinkAPIException te) {
	    te.printStackTrace(System.err);
	    Assert.fail(te.getMessage());
	}

	File attachmentFile = new File("/tmp/jen-2.png");

	String fileContent = null;

	try {
	    byte[] byteArray = FileUtils.readFileToByteArray(attachmentFile);
	    fileContent = new String(Base64.encodeBase64(byteArray));
	} catch (IOException e) {
	    e.printStackTrace(System.err);
	    System.exit(-1);
	}

	Attachment attachment = api.uploadExecutionAttachment(13, // executionId
		"Setting customer plan", // title
		"In this screen the attendant is defining the customer plan", // description
		"screenshot_customer_plan_" + System.currentTimeMillis()
			+ ".jpg", // fileName
		"image/jpeg", // fileType
		fileContent); // content

	System.out.println("Attachment uploaded");
	
	Assert.assertNotNull(attachment);
    }

}
