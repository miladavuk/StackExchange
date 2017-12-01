package classifier;

public class ClassifierMatrix {

	private double accuracy;
	private double precision;
	private double recall;
	private double Fmeasure;
	private double meanAbsoluteError;
	private double rootMeanSquaredError;
	private double relativeAbsoluteError;
	private double rootRelativeSquaredError;
	private double kappaStatistic;
	public ClassifierMatrix(double accuracy, double precision, double recall, double Fmeasure,
			double meanAbsoluteError, double rootMeanSquaredError, double relativeAbsoluteError,
			double rootRelativeSquaredError, double kappaStatistic) {
		super();
		this.accuracy = accuracy;
		this.precision = precision;
		this.recall = recall;
		this.Fmeasure = Fmeasure;
		this.meanAbsoluteError = meanAbsoluteError;
		this.rootMeanSquaredError = rootMeanSquaredError;
		this.relativeAbsoluteError = relativeAbsoluteError;
		this.rootRelativeSquaredError = rootRelativeSquaredError;
		this.kappaStatistic = kappaStatistic;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	public double getPrecision() {
		return precision;
	}
	public void setPrecision(double precision) {
		this.precision = precision;
	}
	public double getRecall() {
		return recall;
	}
	public void setRecall(double recall) {
		this.recall = recall;
	}
	public double getFmeasure() {
		return Fmeasure;
	}
	public void setFmeasure(double sumFmeasure) {
		this.Fmeasure = sumFmeasure;
	}
	public double getMeanAbsoluteError() {
		return meanAbsoluteError;
	}
	public void setMeanAbsoluteError(double meanAbsoluteError) {
		this.meanAbsoluteError = meanAbsoluteError;
	}
	public double getRootMeanSquaredError() {
		return rootMeanSquaredError;
	}
	public void setRootMeanSquaredError(double rootMeanSquaredError) {
		this.rootMeanSquaredError = rootMeanSquaredError;
	}
	public double getRelativeAbsoluteError() {
		return relativeAbsoluteError;
	}
	public void setRelativeAbsoluteError(double relativeAbsoluteError) {
		this.relativeAbsoluteError = relativeAbsoluteError;
	}
	public double getRootRelativeSquaredError() {
		return rootRelativeSquaredError;
	}
	public void setRootRelativeSquaredError(double rootRelativeSquaredError) {
		this.rootRelativeSquaredError = rootRelativeSquaredError;
	}
	public double getKappaStatistic() {
		return kappaStatistic;
	}
	public void setKappaStatistic(double kappaStatistic) {
		this.kappaStatistic = kappaStatistic;
	}
	@Override
	public String toString() {
		return "ClassifierMatrix [accuracy=" + accuracy + "%, precision=" + precision + "%, recall=" + recall
				+ "%, sumFmeasure=" + Fmeasure + "%, meanAbsoluteError=" + meanAbsoluteError
				+ ", rootMeanSquaredError=" + rootMeanSquaredError + ", relativeAbsoluteError=" + relativeAbsoluteError
				+ "%, rootRelativeSquaredError=" + rootRelativeSquaredError + "%, kappaStatistic=" + kappaStatistic + "]";
	}

	
}