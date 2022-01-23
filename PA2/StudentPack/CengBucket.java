import java.util.Vector;
public class CengBucket {

	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	public int hashLength;
	public String hashPref;
	public Vector<CengPoke> pokes;

	public CengBucket(int hashL){
		pokes = new Vector<CengPoke>();
		hashLength = hashL;
	}
	
	public int pokeCount()
	{
		// TODO: Return the pokemon count in the bucket.
		return 0;		
	}
	
	public CengPoke pokeAtIndex(int index)
	{
		// TODO: Return the corresponding pokemon at the index.
		return null;
	}
	
	public int getHashPrefix()
	{
		// TODO: Return hash prefix length.
		return 0;
	}
	
	public Boolean isVisited()
	{
		// TODO: Return whether the bucket is found while searching.
		return false;		
	}

	void CengBucketPrint(){
		System.out.println("\t\t"+"\"bucket\": {");
		System.out.println("\t\t\t"+"\"hashLength\": "+Integer.toString(hashLength)+",");
		System.out.println("\t\t\t"+"\"pokes\": [");
		for(int i=0;i<pokes.size();i++){
			CengPoke p = pokes.get(i);
			if(i==pokes.size()-1){
				p.CengPokePrint("");
			}
			else{
				p.CengPokePrint(",");
			}

		}
		System.out.println("\t\t\t"+"]");
		System.out.println("\t\t"+"}");

	}
	// Own Methods
}
