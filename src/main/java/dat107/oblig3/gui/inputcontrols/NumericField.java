package dat107.oblig3.gui.inputcontrols;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * A textfield which only allows numbers.
 */
@SuppressWarnings("serial")
public class NumericField extends ToggleableTextField {
	
	public NumericField(boolean withDecimals) {
		setNumericDocumentFilter(withDecimals);
	}
	
	public NumericField() {
		this(false);
	}
	
	public NumericField(int length, boolean withDecimals) {
		super(length);
		setNumericDocumentFilter(withDecimals);
	}
	
	public NumericField(int length) {
		this(length, false);
	}
	
	private void setNumericDocumentFilter(boolean withDecimals) {
		if(getDocument() instanceof AbstractDocument) {
			AbstractDocument doc = (AbstractDocument)getDocument();
			
			doc.setDocumentFilter(new NumericDocumentFilter(withDecimals));
		}
	}
	
	public Number getNumber() {
		try {
			return NumberFormat.getInstance().parse(getText());
		} catch (ParseException e) {
			throw new IllegalArgumentException("Unexpected error.");
		}
	}
	
	public int getInt() {
		return getNumber().intValue();
	}
	
	public double getDouble() {
		return getNumber().doubleValue();
	}
	
	public class NumericDocumentFilter extends DocumentFilter {
		
		private boolean includeDecimal = false;
		
		public NumericDocumentFilter() {};
		
		public NumericDocumentFilter(boolean includeDecimal) {
			this.includeDecimal = includeDecimal;
		}
		
		@Override
	    public void insertString(DocumentFilter.FilterBypass fb, int offset,
	    		String text, AttributeSet attr)
	            throws BadLocationException {
			StringBuilder buffer = new StringBuilder(text.length());
			
	        for (int i = 0; i < text.length(); i++) {
	            char ch = text.charAt(i);
	            if (isNumber(ch) || (includeDecimal && isDecimalSeperator(ch))) {
	                buffer.append(ch);
	            }
	        }
	        
	        super.insertString(fb, offset, buffer.toString(), attr);
	    }

	    @Override
	    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, 
	    		String string, AttributeSet attr) 
	    		throws BadLocationException {
	        if (length > 0) {
	            fb.remove(offset, length);
	        }
	        
	        insertString(fb, offset, string, attr);
	    }
	    
		private boolean isNumber(char ch) {
			return (ch == '0') || (ch == '1') ||(ch == '2') || (ch == '3')
					|| (ch == '4') || (ch == '5') || (ch == '6') || (ch == '7')
					|| (ch == '8') || (ch == '9');
		}
		
		private boolean isDecimalSeperator(char ch) {
			return (ch == ',') || (ch == '.');
		}
		
	}
}
