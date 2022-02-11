import java.util.Vector;

public class CengHashTable {

	public Vector<CengHashRow> rows;
	public int bitNumber;
	public int globalDepth;
	public int emptyBucket;

	public CengHashTable()
	{
		// Create a hash table with only 1 row.

		rows = new Vector<CengHashRow>();
		CengHashRow row = new CengHashRow("0");
		rows.add(row);
		globalDepth = 0;
		emptyBucket = 1;
		bitNumber = (int) (Math.log(CengPokeKeeper.getHashMod())/ Math.log(2));
	}

	public void deletePoke(Integer pokeKey)
	{
		int hashedVal = pokeKey % CengPokeKeeper.getHashMod();
		String binaryString = Integer.toBinaryString(hashedVal);
		int len = binaryString.length();
		String hash;
		if(len==bitNumber){
			hash = binaryString;
		}
		else{
			int appendZero = bitNumber-len;
			hash = "0".repeat(appendZero)+binaryString;
		}
		CengHashRow r;
		boolean deleted = false;
		for(int i=0;i<rows.size();i++){
			r = rows.get(i);
			if(hash.startsWith(r.hashPref.substring(0,globalDepth))){
				for(int j=0;j<r.bucket.pokes.size();j++){
					if(r.bucket.pokes.get(j).pokeKey().equals(pokeKey)){
						r.bucket.pokes.remove(j);
						j--;
						if(r.bucket.pokes.size()==0){
							r.bucket.empty=true;
							emptyBucket++;
						}
						deleted=true;
						break;
					}
				}
				if(deleted){
					break;
				}
			}

		}
		System.out.println("\"delete\": {");
		System.out.println("\t\"emptyBucketNum\": " + Integer.toString(emptyBucket));
		System.out.println("}");
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
					if(b.empty){
						emptyBucket--;
						b.empty = false;
					}
				}
				else{
					//split
					b.hashPref = "0";
					b.hashLength++;
					CengBucket newBucket2 = new CengBucket(1);
					newBucket2.hashPref =  "1";

					// distribute again
//					System.out.println("distribution starts");
//					System.out.println("b.pokes.size(): "+ b.pokes.size());
					for(int i=0;i<b.pokes.size();i++){
						CengPoke p = b.pokes.get(i);
//						System.out.println("poke hash: "+p.hash);
//						System.out.println("b.hashpref: "+b.hashPref);

						if(!p.hash.startsWith(b.hashPref)){
//							System.out.println("p.hash starts with b.hashref: "+p.hash.startsWith(b.hashPref));
							b.pokes.remove(i);
							i--;
//							System.out.println("poke with hash:"+ p.hash+" added to new bucket");
							newBucket2.pokes.add(p);
						}
					}
					if(newBucket2.pokes.size()==0){
						emptyBucket++;
					}
					else{
						newBucket2.empty = false;
						if(b.pokes.size()==0){
							emptyBucket++;
							b.empty = true;
						}
					}

					// double directory
					CengHashRow newRow = new CengHashRow("1");
					globalDepth++;

					if(poke.hash.startsWith(b.hashPref)){
						if (b.pokes.size()< bucketSize){
							b.pokes.add(poke);
							added=true;
							if(b.empty){
								emptyBucket--;
								b.empty=false;
							}
						}
					}
					else{
						if(newBucket2.pokes.size()<bucketSize){
							newBucket2.pokes.add(poke);
							added = true;
							if(newBucket2.empty){
								emptyBucket--;
								newBucket2.empty=false;
							}
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
							if(b.empty){
								emptyBucket--;
								b.empty=false;
							}
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
										j--;
										newBucket.pokes.add(p);
									}
								}
								if(newBucket.pokes.size()==0){
									emptyBucket++;
								}
								else{
									newBucket.empty = false;
									if(b.pokes.size()==0){
										emptyBucket++;
										b.empty = true;
									}
								}

								if(poke.hash.startsWith(b.hashPref)){
									if (b.pokes.size()< bucketSize){
										b.pokes.add(poke);
										added=true;
										if(b.empty){
											emptyBucket--;
											b.empty=false;
										}
									}
								}
								else{
									if(newBucket.pokes.size()<bucketSize){
										newBucket.pokes.add(poke);
										added = true;
										if(newBucket.empty){
											emptyBucket--;
											newBucket.empty=false;
										}
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
									for(int x=0; x<rows.size();x++){
										CengHashRow hr;
										hr = rows.get(x);
										if(!hr.hashPref.startsWith(hr.bucket.hashPref)){
											hr.bucket=newBucket;
										}
									}
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
		int hashedVal = pokeKey % CengPokeKeeper.getHashMod();
		String binaryString = Integer.toBinaryString(hashedVal);
		int len = binaryString.length();
		String hash;
		if(len==bitNumber){
			hash = binaryString;
		}
		else{
			int appendZero = bitNumber-len;
			hash = "0".repeat(appendZero)+binaryString;
		}
		CengHashRow r;
		Vector<CengHashRow> searchResult = new Vector<CengHashRow>();
		for(int i=0;i<rows.size();i++){
			r = rows.get(i);
			CengBucket b = r.bucket;
			if(hash.startsWith(b.hashPref.substring(0, b.hashLength))){
				for(int j=0;j<r.bucket.pokes.size();j++){
					if(r.bucket.pokes.get(j).pokeKey().equals(pokeKey)){
						searchResult.add(r);
						break;
					}
				}
			}
		}
		System.out.println("\"search\": {");
		for(int i = 0; i< searchResult.size();i++ ){
			r = searchResult.get(i);
			if(i==searchResult.size()-1){
				r.CengHashRowPrint("");
			}
			else{
				r.CengHashRowPrint(",");
			}

		}
		System.out.println("}");


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
