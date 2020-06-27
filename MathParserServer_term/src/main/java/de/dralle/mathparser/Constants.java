/**
 * 
 */
package de.dralle.mathparser;

/**
 * Enum over all supported constants.
 * 
 * @author Nils Dralle
 *
 */
public enum Constants {
	ALPHA("\\ALPHA"),
	AALPHA("\\AALPHA"),
	BETA("\\BETA"),
	BBETA("\\BBETA"),
	GAMMA("\\GAMMA"),
	GGAMMA("\\GGAMMA"),
	DELTA("\\DELTA"),
	DDELTA("\\DDELTA"),
	EPSILON("\\EPSILON"),
	EEPSILON("\\EEPSILON"),
	VAREPSILON("\\VAREPSILON"),
	ETA("\\ETA"),
	EETA("\\EETA"),
	ZETA("\\ZETA"),
	ZZETA("\\ZZETA"),
	THETA("\\THETA"),
	TTHETA("\\TTHETA"),
	VARTHETA("\\VARTHETA"),
	IOTA("\\IOTA"),
	IIOTA("\\IIOTA"),
	KAPPA("\\KAPPA"),
	KKAPPA("\\KKAPPA"),
	VARKAPPA("\\VARKAPPA"),
	LAMBDA("\\LAMBDA"),
	LLAMBDA("\\LLAMBDA"),
	MU("\\MU"),
	MMU("\\MMU"),
	NU("\\NU"),
	NNU("\\NNU"),
	XI("\\XI"),
	XXI("\\XXI"),
	PI("\\PI"),
	PPI("\\PPI"),
	PHI("\\PHI"),
	VARPHI("\\VARPHI"),
	PPHI("\\PPHI"),
	RHO("\\RHO"),
	RRHO("\\RRHO"),
	VARRHO("\\VARRHO"),
	SIGMA("\\SIGMA"),
	SSIGMA("\\SSIGMA"),
	VARSIGMA("\\VARSIGMA"),
	TAU("\\TAU"),
	TTAU("\\TTAU"),
	UPSILON("\\UPSILON"),
	UUPSILON("\\UUPSILON"),
	CHI("\\CHI"),
	CCHI("\\CCHI"),
	PSI("\\PSI"),
	PPSI("\\PPSI"),
	OMEGA("\\OMEGA"),
	OOMEGA("\\OOMEGA"),
	OMICRON("\\OMICRON"),
	OOMICRON("\\OOMICRON");
	
	private Constants(String name) {
		this.name = name;
	}

	private String name;

	/**
	 * 
	 * @return Name of the constant as string. 
	 */
	public String getName() {
		return name;
	}
}
