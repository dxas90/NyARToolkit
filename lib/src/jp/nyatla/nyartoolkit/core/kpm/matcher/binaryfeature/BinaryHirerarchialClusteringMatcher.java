package jp.nyatla.nyartoolkit.core.kpm.matcher.binaryfeature;

import jp.nyatla.nyartoolkit.core.kpm.freak.FreakFeaturePoint;
import jp.nyatla.nyartoolkit.core.kpm.freak.FreakFeaturePointStack;
import jp.nyatla.nyartoolkit.core.kpm.keyframe.BinaryHierarchicalClustering;
import jp.nyatla.nyartoolkit.core.kpm.keyframe.FreakMatchPointSetStack;
import jp.nyatla.nyartoolkit.core.kpm.keyframe.Keyframe;
import jp.nyatla.nyartoolkit.core.kpm.matcher.FeaturePairStack;


final public class BinaryHirerarchialClusteringMatcher extends BinaryFeatureMatcher
{
	public BinaryHirerarchialClusteringMatcher() {
		super();
	}

	/**
	 * Match two feature stores with an index on features2.
	 * 
	 * @return Number of matches
	 */
	@Override
	public int match(FreakFeaturePointStack i_query, Keyframe i_key_frame,FeaturePairStack i_maches)
	{
		//indexが無いときはベースクラスを使う。
		BinaryHierarchicalClustering index2=i_key_frame.getIndex();
		if(index2==null){
			return super.match(i_query, i_key_frame, i_maches);
		}
		FreakMatchPointSetStack ref=i_key_frame.getFeaturePointSet();
		if (i_query.getLength()*ref.getLength() == 0) {
			return 0;
		}
		FreakFeaturePoint[] query_buf=i_query.getArray();
		FreakMatchPointSetStack.Item[] ref_buf=ref.getArray();
		int q_len=i_query.getLength();
		
		for (int i = 0; i < q_len; i++) {
			int first_best = Integer.MAX_VALUE;// std::numeric_limits<unsigned int>::max();
			int second_best = Integer.MAX_VALUE;// std::numeric_limits<unsigned int>::max();
			int best_index = Integer.MAX_VALUE;// std::numeric_limits<int>::max();

			// Perform an indexed nearest neighbor lookup
			FreakFeaturePoint fptr1 = query_buf[i];
			index2.query(fptr1.descripter);

			// Search for 1st and 2nd best match
			int[] v = index2.reverseIndex();
			for (int j = 0; j < v.length; j++) {
				FreakFeaturePoint fptr2=ref_buf[v[j]];
				// Both points should be a MINIMA or MAXIMA
				if (fptr1.maxima != fptr2.maxima) {
					continue;
				}

				int d = fptr1.descripter.hammingDistance(fptr2.descripter);
				if (d < first_best) {
					second_best = first_best;
					first_best = d;
					best_index = v[j];
				} else if (d < second_best) {
					second_best = d;
				}
			}

			// Check if FIRST_BEST has been set
			if (first_best != Integer.MAX_VALUE) {

				// If there isn't a SECOND_BEST, then always choose the FIRST_BEST.
				// Otherwise, do a ratio test.
				if (second_best == Integer.MAX_VALUE) {
					FeaturePairStack.Item t = i_maches.prePush();
					t.query=fptr1;
					t.ref=ref_buf[best_index];
				} else {
					// Ratio test
					double r = (double) first_best / (double) second_best;
					if (r < mThreshold) {
						// mMatches.push_back(match_t((int)i, best_index));
						FeaturePairStack.Item t = i_maches.prePush();
						t.query=fptr1;
						t.ref=ref_buf[best_index];
					}
				}
			}
		}
		return i_maches.getLength();
	}
}