package com.afrologix.kahoula.resources.Location;
import com.afrologix.kahoula.resources.City.CityDTO;
import com.afrologix.kahoula.resources.Region.Region;
import com.afrologix.kahoula.resources.Region.RegionDTO;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Location entity.
 */
public class LocationDTO implements Serializable {

    private String id;

    @NotNull
    private RegionDTO region;

    @NotNull
    private CityDTO city;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotNull RegionDTO getregion() {
        return region;
    }

    public void setregion(RegionDTO region) {
        this.region = region;
    }

    public @NotNull CityDTO getcity() {
        return city;
    }

    public void setcity(CityDTO city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationDTO locationDTO = (LocationDTO) o;
        if (locationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
            "id=" + getId() +
            ", region='" + getregion() + "'" +
            ", city='" + getcity() + "'" +
            "}";
    }
}
