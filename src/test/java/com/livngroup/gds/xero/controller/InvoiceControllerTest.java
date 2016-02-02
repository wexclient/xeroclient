package com.livngroup.gds.xero.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.connectifier.xeroclient.XeroClient;
import com.connectifier.xeroclient.models.Contact;
import com.connectifier.xeroclient.models.Invoice;
import com.connectifier.xeroclient.models.InvoiceType;
import com.connectifier.xeroclient.models.LineItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.livngroup.gds.xero.ServerApp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServerApp.class)
@WebAppConfiguration
public class InvoiceControllerTest {

	@Mock(name = "xeroClient")
	private XeroClient xeroClientMock;
	
	@InjectMocks
	private InvoiceController invoiceController;
	
	private MockMvc mockMvc;

    private final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;
	private final ObjectMapper JSON_MAPPER = new ObjectMapper();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(invoiceController).build();
	}  
	
	@Test
	public void testCreateInvoice() throws Exception {
		
		List<Invoice> invoiceList = Collections.emptyList();
		when(xeroClientMock.createInvoice(Matchers.<Invoice>any())).thenReturn(invoiceList);
		
		mockMvc.perform(put("http://localhost:8080/invoice/create")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(JSON_MAPPER.writeValueAsString(createInvoice())))
		.andExpect(status().isOk())
		.andExpect(content().contentType(CONTENT_TYPE))
		.andExpect(content().json("{'ok': true}", false))
		.andExpect(content().json("{'message': 'Success'}", false));
		 
		verify(xeroClientMock, times(1)).createInvoice(Matchers.<Invoice>any());
		verifyNoMoreInteractions(xeroClientMock);
	}

	@Test
	public void testValidationCreateInvoice() throws Exception {
		Invoice invoice = createInvoice();
		invoice.setInvoiceNumber(null);

		mockMvc.perform(put("http://localhost:8080/invoice/create")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(JSON_MAPPER.writeValueAsString(invoice)))
		.andExpect(status().isNotAcceptable());
	
	}

	@Test
	public void testMethodForCreateInvoice() throws Exception {
		mockMvc.perform(get("http://localhost:8080/invoice/create")).andExpect(status().isMethodNotAllowed());
		mockMvc.perform(post("http://localhost:8080/invoice/create")).andExpect(status().isMethodNotAllowed());
		mockMvc.perform(delete("http://localhost:8080/invoice/create")).andExpect(status().isMethodNotAllowed());
		verify(xeroClientMock, times(0)).createInvoice(Matchers.<Invoice>any());
	}
	
	private Invoice createInvoice() {
		Contact contact = new Contact();
		contact.setName("contact-name1");

		LineItem lineItem = new LineItem(); 
        lineItem.setUnitAmount(new BigDecimal("100.50"));
        lineItem.setQuantity(new BigDecimal("1"));
        lineItem.setLineAmount(new BigDecimal("100.50"));
        lineItem.setDescription("testing invoice");
		
		Invoice invoice = new Invoice();
		invoice.setInvoiceNumber("1234567891100-supa-new");
		invoice.setType(InvoiceType.ACCREC);
		invoice.setDate(new Date());
		invoice.setDueDate(new Date());
		invoice.setContact(contact);
		invoice.setLineItems(Arrays.asList(lineItem));
		
		return invoice;		
	} 

}
