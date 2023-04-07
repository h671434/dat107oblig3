package dat107.oblig3.gui.inputcontrols;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DateField extends JPanel {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyy");
	
	private NumericField day = new NumericField(2);
	private NumericField month = new NumericField(2);
	private NumericField year = new NumericField(4);
	
	public DateField() {
		add(day);
		add(month);
		add(year);
	}
	
	public Date getDate() {
		if(!inputIsValid()) {
			throw new NumberFormatException();
		}

		try {
			return DATE_FORMAT.parse(day.getInt() 
					+ "-" + month.getInt() 
					+ "-" +  year.getInt());
		} catch (ParseException e) {
			throw new NumberFormatException();
		}
	}
	
	public void setDate(Date date) {
		String dateString = DATE_FORMAT.format(date);
		
		day.setText(dateString.substring(0, 2));
		month.setText(dateString.substring(3, 5));
		year.setText(dateString.substring(6, 10));
	}
	
	public void setDateEmpty() {
		day.setText("");
		month.setText("");
		year.setText("");
	}
	
	public void setEditable(boolean editable) {
		day.setEditable(editable);
		month.setEditable(editable);
		year.setEditable(editable);
	}
	
	public void setToolTipText(String tooltip) {
		super.setToolTipText(tooltip);
		day.setToolTipText(tooltip);
		month.setToolTipText(tooltip);
		year.setToolTipText(tooltip);
	}
	
	public boolean inputIsValid() {
		try {
			DATE_FORMAT.parse(day.getInt() 
					+ "-" + month.getInt() 
					+ "-" +  year.getInt());
			
			return true;
			
		} catch(IllegalArgumentException | ParseException e) {
			return false;
		}
	}

}
