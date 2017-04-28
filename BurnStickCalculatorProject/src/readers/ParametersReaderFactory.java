package readers;

/*
 * This is a factory class for parameters readers.
 */
public class ParametersReaderFactory {
	
	/*
	 * Receives the name of a particular parameters reader.
	 * If there is no such reader returns null.
	 */
	public static ParametersReader createReader(String nameReader){
		
		ParametersReader reader = null;
		switch (nameReader) {
		case "ParametersTextFileReader":
			reader = new ParametersTextFileReader();
			break;

		default:
			break;
		}
		
		return reader;
	}
}
