import java.util.Vector;

public class CengHashRow {

	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	public String hashPref;
	public CengBucket bucket;
	public CengHashRow(String prefix)
	{
		hashPref = prefix;
		bucket = new CengBucket(0);
	}
	
	public String hashPrefix()
	{
		// TODO: Return row's hash prefix (such as 0, 01, 010, ...)
		return "-1";		
	}
	
	public CengBucket getBucket()
	{
		// TODO: Return the bucket that the row points at.
		return null;		
	}
	
	public boolean isVisited()
	{
		// TODO: Return whether the row is used while searching.
		return false;		
	}

	void CengHashRowPrint(String comma){
		System.out.println("\t"+"\"row\": {");
		System.out.println("\t\t"+"\"hashPref\": " + hashPref+",");
		bucket.CengBucketPrint();
		System.out.println("\t"+"}"+comma);


	}
	
	// Own Methods
}
