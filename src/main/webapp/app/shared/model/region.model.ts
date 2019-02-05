export interface IRegion {
    id?: string;
    name?: string;
}

export class Region implements IRegion {
    constructor(public id?: string, public name?: string) {}
}
