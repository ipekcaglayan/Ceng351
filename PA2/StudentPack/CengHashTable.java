import java.util.Vector;

public class CengHashTable {

	public Vector<CengHashRow> rows;
	public int bitNumber;
	public int globalDepth;

	public CengHashTable()
	{
		// Create a hash table with only 1 row.

		rows = new Vector<CengHashRow>();
		CengHashRow row = new CengHashRow("0");
		rows.add(row);
		globalDepth = 0;
	}

	public void deletePoke(Integer pokeKey)
	{
		// TODO: Empty Implementation
	}

	public void addPoke(CengPoke poke)
	{

		int bucketSize = CengPokeKeeper.getBucketSize();
		//set hash of poke
		int hashedVal = poke.pokeKey() % CengPokeKeeper.getHashMod();
		String binaryString = Integer.toBinaryString(hashedVal);
		int len = binaryString.length();
		if(len==bitNumber){
			poke.hash = binaryString;
		}
		else{
			int appendZero = bitNumber-len;
			poke.hash = "0".repeat(appendZero)+binaryString;
		}

		boolean added = false;

		while(!added){
			if(rows.size()==1){
				CengBucket b = rows.get(0).bucket;
				if(b.pokes.size()<bucketSize){
					b.pokes.add(poke); //new poke inserted.
					added=true;
				}
				else{
					//split
					b.hashPref = "0";
					b.hashLength++;
					CengBucket newBucket2 = new CengBucket(1);
					newBucket2.hashPref =  "1";

					// distribute again

					for(int i=0;i<b.pokes.size();i++){
						CengPoke p = b.pokes.get(i);
						if(!p.hash.startsWith(b.hashPref)){
							b.pokes.remove(i);
							newBucket2.pokes.add(p);
						}
					}

					// double directory
					CengHashRow newRow = new CengHashRow("1");
					globalDepth++;

					if(poke.hash.startsWith(b.hashPref)){
						if (b.pokes.size()< bucketSize){
							b.pokes.add(poke);
							added=true;
						}
					}
					else{
						if(newBucket2.pokes.size()<bucketSize){
							newBucket2.pokes.add(poke);
							added = true;
						}
					}

					newRow.bucket = newBucket2;
					rows.add(newRow);



				}

			}

			else{
				CengHashRow r;
				CengBucket b;
				for(int i=0;i<rows.size();i++){
					r = rows.get(i);
					if(poke.hash.startsWith(r.hashPref.substring(0, globalDepth) )){
						b = r.bucket;
						if(b.pokes.size()<bucketSize){
							b.pokes.add(poke);
							added=true;
							break;
						}
						else{
							//split while buraya
							while(!added){
								CengBucket newBucket = new CengBucket(b.hashLength+1);
								b.hashLength++;
								newBucket.hashPref = b.hashPref+"1";
								b.hashPref += "0";
								//distribute again
								for(int j=0;j<b.pokes.size();j++){
									CengPoke p = b.pokes.get(j);
									if(!p.hash.startsWith(b.hashPref)){
										b.pokes.remove(j);
										newBucket.pokes.add(p);
									}
								}

								if(poke.hash.startsWith(b.hashPref)){
									if (b.pokes.size()< bucketSize){
										b.pokes.add(poke);
										added=true;
									}
								}
								else{
									if(newBucket.pokes.size()<bucketSize){
										newBucket.pokes.add(poke);
										added = true;
									}
								}
								// double dir if necessary
								if(b.hashLength>globalDepth){
									globalDepth++;
									Vector<CengHashRow> newRowsArray = new Vector<CengHashRow>();
									for(int j=0;j<rows.size();j++){
										CengHashRow loopRow = rows.get(j);
										if(loopRow==r){
											CengHashRow newRow = new CengHashRow(loopRow.hashPref+"1");
											loopRow.hashPref +="0";

											if(b.hashPref.startsWith(r.hashPref.substring(0, b.hashLength))){
												newRow.bucket = newBucket;
											}
											else{
												loopRow.bucket = newBucket;
												newRow.bucket = b;
											}
											newRowsArray.add(loopRow);
											newRowsArray.add(newRow);
										}
										else{
											CengHashRow newRow = new CengHashRow(loopRow.hashPref+"1");
											loopRow.hashPref +="0";
											newRow.bucket = loopRow.bucket;
											newRowsArray.add(loopRow);
											newRowsArray.add(newRow);

										}
									}
									rows = newRowsArray;
								}
								else{
									r.bucket = newBucket;
								}

							}
							break;

						}
					}
				}
			}
		}


//		if(globalDepth==0){
//			CengHashRow r = rows.get(0);
//			if(r.bucket.pokes.size() < bucketSize){
//				r.bucket.pokes.add(poke);
//			}
//			else{
//				//double directory
//			}
//		}
//		else{
//			String checkStr = poke.hash.substring(0, globalDepth);
//			for(int i=0;i<rows.size();i++){
//				CengHashRow insertRow =rows.get(i);
//				if(insertRow.hashPref.startsWith(checkStr)){
//					CengBucket b = insertRow.bucket;
//					if(b.pokes.size()<bucketSize){
//						b.pokes.add(poke);
//					}
//					else{
//						if(globalDepth>)
//					}
//				}
//			}
//		}


		// TODO: Empty Implementation
	}
	
	public void searchPoke(Integer pokeKey)
	{
		// TODO: Empty Implementation
	}


	public void print()
	{
		System.out.println("\"table\": {");
		for(int i = 0; i< rows.size();i++ ){
			CengHashRow r = rows.get(i);
			if(i==rows.size()-1){
				r.CengHashRowPrint("");
			}
			else{
				r.CengHashRowPrint(",");
			}

		}
		System.out.println("}");


	}

	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	
	public int prefixBitCount()
	{
		// TODO: Return table's hash prefix length.
		return 0;		
	}
	
	public int rowCount()
	{
		// TODO: Return the count of HashRows in table.
		return 0;		
	}
	
	public CengHashRow rowAtIndex(int index)
	{
		// TODO: Return corresponding hashRow at index.
		return null;
	}


	
	// Own Methods
}
