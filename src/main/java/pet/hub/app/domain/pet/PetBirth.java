package pet.hub.app.domain.pet;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetBirth {
    private String year;
    private String month;
    private String day;

    private void setYear(final String year) {
        if (year != null) {
            this.year = year;
        }
    }

    private void setMonth(final String month) {
        if (month != null) {
            this.month = month;
        }
    }

    private void setDay(final String day) {
        if (day != null) {
            this.day = day;
        }
    }

    protected void setPetBirth(final PetBirth petBirth) {
        setYear(petBirth.getYear());
        setMonth(petBirth.getMonth());
        setDay(petBirth.getDay());
    }
}
