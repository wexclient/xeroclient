package com.livngroup.gds.xero.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.connectifier.xeroclient.models.Invoice;
import com.livngroup.gds.xero.exception.XeroAppException;
import com.livngroup.gds.xero.response.XeroCallResponse;

@RestController
@RequestMapping("/invoice")
public class InvoiceController extends XeroController {

	@RequestMapping(value="/create", produces="application/json", method=RequestMethod.PUT)
	public @ResponseBody XeroCallResponse<List<Invoice>> createInvoice(@RequestBody Invoice invoice) throws XeroAppException {
		
		assertNotNull("invoice", invoice); 
		assertNotEmpty("invoice number", invoice.getInvoiceNumber());
		assertEmpty("invoice id", invoice.getInvoiceID());
		
		List<Invoice> invoiceList = xeroClient.createInvoice(invoice);

		return XeroCallResponse.forSuccess(invoiceList);
	}

	@RequestMapping(value="/update", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody XeroCallResponse<List<Invoice>> updateInvoice(@RequestBody Invoice invoice) throws XeroAppException {
		
		assertNotNull("invoice", invoice); 
		assertNotEmpty("invoice id", invoice.getInvoiceID());
		
		// do not be confused. "createInvoice" in fact updates if parameters are correct
		List<Invoice> invoiceList = xeroClient.createInvoice(invoice);

		return XeroCallResponse.forSuccess(invoiceList);
	}

	@RequestMapping(value="/get/{invoiceNo}", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody XeroCallResponse<Invoice> getInvoice(@PathVariable("invoiceNo") String invoiceNo) throws XeroAppException {
		
		assertNotEmpty("invoice number", invoiceNo);
		
		Invoice invoice = xeroClient.getInvoice(invoiceNo);

		return XeroCallResponse.forSuccess(invoice);
	}

	@RequestMapping(value="/print/{invoiceNo}", produces="application/pdf", method=RequestMethod.GET)
	public @ResponseBody XeroCallResponse<Invoice> printInvoice(@PathVariable("invoiceNo") String invoiceNo) throws XeroAppException {
		
		assertNotEmpty("invoice number", invoiceNo);
		
		Invoice invoice = xeroClient.getInvoice(invoiceNo);

		return XeroCallResponse.forSuccess(invoice);
	}

}