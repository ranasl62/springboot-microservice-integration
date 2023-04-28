package scoringhelper.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RecordSI {
    @Id
    private String id;

    private Integer currentSI;

    private Integer upperBound;

    private Integer lowerBound;

    public RecordSI( Integer currentSI, Integer lowerBound, Integer upperBound) {
        this.currentSI = currentSI;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public String getId() {
        return id;
    }


    public Integer getCurrentSI() {
        return currentSI;
    }

    public void setCurrentSI(Integer currentSI) {
        this.currentSI = currentSI;
    }

    public Integer getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Integer upperBound) {
        this.upperBound = upperBound;
    }

    public Integer getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Integer lowerBound) {
        this.lowerBound = lowerBound;
    }
}
