export interface ILocation {
    id?: string;
    regionName?: string;
    cityName?: string;
}

export class Location implements ILocation {
    constructor(public id?: string, public regionName?: string, public cityName?: string) {}
}
