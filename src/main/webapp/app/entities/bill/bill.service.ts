import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBill } from 'app/shared/model/bill.model';

type EntityResponseType = HttpResponse<IBill>;
type EntityArrayResponseType = HttpResponse<IBill[]>;

@Injectable({ providedIn: 'root' })
export class BillService {
    public resourceUrl = SERVER_API_URL + 'api/bills';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/bills';

    constructor(protected http: HttpClient) {}

    create(bill: IBill): Observable<EntityResponseType> {
        return this.http.post<IBill>(this.resourceUrl, bill, { observe: 'response' });
    }

    update(bill: IBill): Observable<EntityResponseType> {
        return this.http.put<IBill>(this.resourceUrl, bill, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IBill>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBill[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBill[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
