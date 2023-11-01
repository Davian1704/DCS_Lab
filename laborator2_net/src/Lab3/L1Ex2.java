package Lab3;

import java.util.EnumMap;
import java.util.Map;

import Fuzzy.FuzzyDefuzzy;
import Fuzzy.FuzzyToken;
import Fuzzy.FuzzyValue;
import Fuzzy.TwoXTwoTable;

public class L1Ex2 {
	public static TwoXTwoTable createInversor() {

		// construct tabela1 FLRS for inversor, first output
		Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable1 = new EnumMap<>(FuzzyValue.class);

		Map<FuzzyValue, FuzzyValue> nlLine = new EnumMap<>(FuzzyValue.class);
		ruleTable1.put(FuzzyValue.NL, nlLine);
		nlLine.put(FuzzyValue.NL, FuzzyValue.NL);
		nlLine.put(FuzzyValue.NM, FuzzyValue.NL);
		nlLine.put(FuzzyValue.ZR, FuzzyValue.NL);
		nlLine.put(FuzzyValue.PM, FuzzyValue.NM);
		nlLine.put(FuzzyValue.PL, FuzzyValue.ZR);

		Map<FuzzyValue, FuzzyValue> nmLine = new EnumMap<>(FuzzyValue.class);
		ruleTable1.put(FuzzyValue.NM, nmLine);
		nmLine.put(FuzzyValue.NL, FuzzyValue.NL);
		nmLine.put(FuzzyValue.NM, FuzzyValue.NL);
		nmLine.put(FuzzyValue.ZR, FuzzyValue.NM);
		nmLine.put(FuzzyValue.PM, FuzzyValue.ZR);
		nmLine.put(FuzzyValue.PL, FuzzyValue.PM);

		Map<FuzzyValue, FuzzyValue> zrLine = new EnumMap<>(FuzzyValue.class);
		ruleTable1.put(FuzzyValue.ZR, zrLine);
		zrLine.put(FuzzyValue.NL, FuzzyValue.NL);
		zrLine.put(FuzzyValue.NM, FuzzyValue.NM);
		zrLine.put(FuzzyValue.ZR, FuzzyValue.ZR);
		zrLine.put(FuzzyValue.PM, FuzzyValue.PM);
		zrLine.put(FuzzyValue.PL, FuzzyValue.PL);

		Map<FuzzyValue, FuzzyValue> pmLine = new EnumMap<>(FuzzyValue.class);
		ruleTable1.put(FuzzyValue.PM, pmLine);
		pmLine.put(FuzzyValue.NL, FuzzyValue.NM);
		pmLine.put(FuzzyValue.NM, FuzzyValue.ZR);
		pmLine.put(FuzzyValue.ZR, FuzzyValue.PM);
		pmLine.put(FuzzyValue.PM, FuzzyValue.PL);
		pmLine.put(FuzzyValue.PL, FuzzyValue.PL);

		Map<FuzzyValue, FuzzyValue> plLine = new EnumMap<>(FuzzyValue.class);
		ruleTable1.put(FuzzyValue.PL, plLine);
		plLine.put(FuzzyValue.NL, FuzzyValue.ZR);
		plLine.put(FuzzyValue.NM, FuzzyValue.PM);
		plLine.put(FuzzyValue.ZR, FuzzyValue.PL);
		plLine.put(FuzzyValue.PM, FuzzyValue.PL);
		plLine.put(FuzzyValue.PL, FuzzyValue.PL);

		// construct tabela2 FLRS for inversor, the second output
		Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable2 = new EnumMap<>(FuzzyValue.class);

		nlLine = new EnumMap<>(FuzzyValue.class);
		ruleTable2.put(FuzzyValue.NL, nlLine);
		nlLine.put(FuzzyValue.NL, FuzzyValue.ZR);
		nlLine.put(FuzzyValue.NM, FuzzyValue.NM);
		nlLine.put(FuzzyValue.ZR, FuzzyValue.NL);
		nlLine.put(FuzzyValue.PM, FuzzyValue.NL);
		nlLine.put(FuzzyValue.PL, FuzzyValue.NL);

		nmLine = new EnumMap<>(FuzzyValue.class);
		ruleTable2.put(FuzzyValue.NM, nmLine);
		nmLine.put(FuzzyValue.NL, FuzzyValue.PM);
		nmLine.put(FuzzyValue.NM, FuzzyValue.ZR);
		nmLine.put(FuzzyValue.ZR, FuzzyValue.NM);
		nmLine.put(FuzzyValue.PM, FuzzyValue.NL);
		nmLine.put(FuzzyValue.PL, FuzzyValue.NL);

		zrLine = new EnumMap<>(FuzzyValue.class);
		ruleTable2.put(FuzzyValue.ZR, zrLine);
		zrLine.put(FuzzyValue.NL, FuzzyValue.PL);
		zrLine.put(FuzzyValue.NM, FuzzyValue.PM);
		zrLine.put(FuzzyValue.ZR, FuzzyValue.ZR);
		zrLine.put(FuzzyValue.PM, FuzzyValue.NM);
		zrLine.put(FuzzyValue.PL, FuzzyValue.NL);

		pmLine = new EnumMap<>(FuzzyValue.class);
		ruleTable2.put(FuzzyValue.PM, pmLine);
		pmLine.put(FuzzyValue.NL, FuzzyValue.PL);
		pmLine.put(FuzzyValue.NM, FuzzyValue.PL);
		pmLine.put(FuzzyValue.ZR, FuzzyValue.PM);
		pmLine.put(FuzzyValue.PM, FuzzyValue.ZR);
		pmLine.put(FuzzyValue.PL, FuzzyValue.NM);

		plLine = new EnumMap<>(FuzzyValue.class);
		ruleTable2.put(FuzzyValue.PL, plLine);
		plLine.put(FuzzyValue.NL, FuzzyValue.PL);
		plLine.put(FuzzyValue.NM, FuzzyValue.PL);
		plLine.put(FuzzyValue.ZR, FuzzyValue.PL);
		plLine.put(FuzzyValue.PM, FuzzyValue.PM);
		plLine.put(FuzzyValue.PL, FuzzyValue.ZR);

		// returning FLRS table with two inputs and two outputs

		return new TwoXTwoTable(ruleTable1, ruleTable2);
	}

	public static void main(String[] args) {

		double w1 = 0.33;
		double w2 = 1.5;

		// specifying the limits for fuzzyfication, defuzzyfication
		FuzzyDefuzzy fuzDefuz = new FuzzyDefuzzy(-1.0, -0.5, 0.0, 0.5, 1.0);

		// creating FLRS table for tow inputs and two outputs
		TwoXTwoTable inversor = createInversor();

		// giving the two inputs
		double x1 = -0.33;
		double x2 = 0.12;

		// multiplying the inputs with the amplification and fuzzyfication factors
		FuzzyToken inpToken1 = fuzDefuz.fuzzyfie(x1 * w1);
		FuzzyToken inpToken2 = fuzDefuz.fuzzyfie(x2 * w2);

		// executing the FLRS table
		FuzzyToken[] out = inversor.execute(inpToken1, inpToken2);

		// displaying the defuzzyfication results
		System.out.println("x3 :: " + fuzDefuz.defuzzify(out[0]));
		System.out.println("x4 :: " + fuzDefuz.defuzzify(out[1]));
	}
}
