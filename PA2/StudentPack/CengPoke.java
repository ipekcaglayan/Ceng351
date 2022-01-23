public class CengPoke {
	
	private Integer pokeKey;
	
	private String pokeName;
	private String pokePower;
	private String pokeType;
	public String hash;
	
	public CengPoke(Integer pokeKey, String pokeName, String pokePower, String pokeType)
	{
		this.pokeKey = pokeKey;
		this.pokeName = pokeName;
		this.pokePower = pokePower;
		this.pokeType = pokeType;
	}
	
	// Getters
	
	public Integer pokeKey()
	{
		return pokeKey;
	}
	public String pokeName()
	{
		return pokeName;
	}
	public String pokePower()
	{
		return pokePower;
	}
	public String pokeType()
	{
		return pokeType;
	}
		
	// GUI method - do not modify
	public String fullName()
	{
		return "" + pokeKey() + "\t" + pokeName() + "\t" + pokePower() + "\t" + pokeType;
	}
		
	// Own Methods

	void CengPokePrint(String comma){
		System.out.println("\t\t\t\t"+"\"poke\": {");
		System.out.println("\t\t\t\t\t"+"\"hash\": "+hash+",");
		System.out.println("\t\t\t\t\t"+"\"pokeKey\": " + Integer.toString(this.pokeKey)+",");
		System.out.println("\t\t\t\t\t"+"\"pokeName\": " + this.pokeName+",");
		System.out.println("\t\t\t\t\t"+"\"pokePower\": " + this.pokePower+",");
		System.out.println("\t\t\t\t\t"+"\"pokeType\": " + this.pokeType);
		System.out.println("\t\t\t\t"+"}"+comma);
	}
}
