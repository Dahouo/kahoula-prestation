export interface ICity {
    id?: string;
    name?: string;
}

export class City implements ICity {
    constructor(public id?: string, public name?: string) {}
}
