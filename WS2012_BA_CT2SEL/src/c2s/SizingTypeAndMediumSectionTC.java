package c2s;

import java.io.Serializable;

public class SizingTypeAndMediumSectionTC implements Serializable {

    private static final long serialVersionUID = 9035843876855301352L;
    private String            name;
    private String            MS_Medium;                // Gas, Liquid...
    private String            MS_SizingStandard;        // API520, ASME VIII
    private String            MS_CDTPCalculation;       // bool
    private String            RF_AD2000A2;              // bool
    private String            RF_API520;                // bool
    private String            RF_ISOCD41269;            // bool
    private String            N_AD2000A2;               // bool
    private String            N_API520;                 // bool
    private String            N_ISOCD41269;             // bool
    private String            AC_FireCase;              // API520, None
    private String            AC_PressureDropInletLine; // ISO..,AD..,None
    private String            AC_BuiltUpBackPressure;   // AD,ISO,None

    public SizingTypeAndMediumSectionTC(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the mS_Medium
     */
    public String getMS_Medium() {
        return MS_Medium;
    }

    /**
     * @param mS_Medium the mS_Medium to set
     */
    public void setMS_Medium(String mS_Medium) {
        MS_Medium = mS_Medium;
    }

    /**
     * @return the mS_SizingStandard
     */
    public String getMS_SizingStandard() {
        return MS_SizingStandard;
    }

    /**
     * @param mS_SizingStandard the mS_SizingStandard to set
     */
    public void setMS_SizingStandard(String mS_SizingStandard) {
        MS_SizingStandard = mS_SizingStandard;
    }

    /**
     * @return the mS_CDTPCalculation
     */
    public String getMS_CDTPCalculation() {
        return MS_CDTPCalculation;
    }

    /**
     * @param mS_CDTPCalculation the mS_CDTPCalculation to set
     */
    public void setMS_CDTPCalculation(String mS_CDTPCalculation) {
        MS_CDTPCalculation = mS_CDTPCalculation;
    }

    /**
     * @return the rF_AD2000A2
     */
    public String getRF_AD2000A2() {
        return RF_AD2000A2;
    }

    /**
     * @param rF_AD2000A2 the rF_AD2000A2 to set
     */
    public void setRF_AD2000A2(String rF_AD2000A2) {
        RF_AD2000A2 = rF_AD2000A2;
    }

    /**
     * @return the rF_API520
     */
    public String getRF_API520() {
        return RF_API520;
    }

    /**
     * @param rF_API520 the rF_API520 to set
     */
    public void setRF_API520(String rF_API520) {
        RF_API520 = rF_API520;
    }

    /**
     * @return the rF_ISOCD41269
     */
    public String getRF_ISOCD41269() {
        return RF_ISOCD41269;
    }

    /**
     * @param rF_ISOCD41269 the rF_ISOCD41269 to set
     */
    public void setRF_ISOCD41269(String rF_ISOCD41269) {
        RF_ISOCD41269 = rF_ISOCD41269;
    }

    /**
     * @return the n_AD2000A2
     */
    public String getN_AD2000A2() {
        return N_AD2000A2;
    }

    /**
     * @param n_AD2000A2 the n_AD2000A2 to set
     */
    public void setN_AD2000A2(String n_AD2000A2) {
        N_AD2000A2 = n_AD2000A2;
    }

    /**
     * @return the n_API520
     */
    public String getN_API520() {
        return N_API520;
    }

    /**
     * @param n_API520 the n_API520 to set
     */
    public void setN_API520(String n_API520) {
        N_API520 = n_API520;
    }

    /**
     * @return the n_ISOCD41269
     */
    public String getN_ISOCD41269() {
        return N_ISOCD41269;
    }

    /**
     * @param n_ISOCD41269 the n_ISOCD41269 to set
     */
    public void setN_ISOCD41269(String n_ISOCD41269) {
        N_ISOCD41269 = n_ISOCD41269;
    }

    /**
     * @return the aC_FireCase
     */
    public String getAC_FireCase() {
        return AC_FireCase;
    }

    /**
     * @param aC_FireCase the aC_FireCase to set
     */
    public void setAC_FireCase(String aC_FireCase) {
        AC_FireCase = aC_FireCase;
    }

    /**
     * @return the aC_PressureDropInletLine
     */
    public String getAC_PressureDropInletLine() {
        return AC_PressureDropInletLine;
    }

    /**
     * @param aC_PressureDropInletLine the aC_PressureDropInletLine to set
     */
    public void setAC_PressureDropInletLine(String aC_PressureDropInletLine) {
        AC_PressureDropInletLine = aC_PressureDropInletLine;
    }

    /**
     * @return the aC_BuiltUpBackPressure
     */
    public String getAC_BuiltUpBackPressure() {
        return AC_BuiltUpBackPressure;
    }

    /**
     * @param aC_BuiltUpBackPressure the aC_BuiltUpBackPressure to set
     */
    public void setAC_BuiltUpBackPressure(String aC_BuiltUpBackPressure) {
        AC_BuiltUpBackPressure = aC_BuiltUpBackPressure;
    }

}
