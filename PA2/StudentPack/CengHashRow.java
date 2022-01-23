import java.util.Vector;

public class CengHashRow {

	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	public String hashPref;
	public Vector<CengBucket> bucket;
	public CengHashRow(String prefix)
	{
		hashPref = prefix;
		bucket = new Vector<CengBucket>();
		bucket.add(new CengBucket(0));
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

	void CengHashRowPrint(){
		System.out.println("\t"+"\"row\": {");
		System.out.println("\t\t"+"\"hashPref\": " + hashPref+",");
		for(int i=0;i<bucket.size();i++){
			bucket.get(i).CengBucketPrint();

		}
		System.out.println("\t"+"}");


	}
	
	// Own Methods
}
