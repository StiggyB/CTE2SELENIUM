package tools;


public class Cast {

	/**
	 * 
	 * 
	 * @param type - to cast to
	 * @param object - to be casted
	 * @return casted object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T as(Class<?> type, Object object) {
		if (type.isInstance(object))
			return (T) type.cast(object);
		return null;
	}

}
