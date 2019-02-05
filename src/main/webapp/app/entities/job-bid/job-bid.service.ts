import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJobBid } from 'app/shared/model/job-bid.model';

type EntityResponseType = HttpResponse<IJobBid>;
type EntityArrayResponseType = HttpResponse<IJobBid[]>;

@Injectable({ providedIn: 'root' })
export class JobBidService {
    public resourceUrl = SERVER_API_URL + 'api/job-bids';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/job-bids';

    constructor(protected http: HttpClient) {}

    create(jobBid: IJobBid): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(jobBid);
        return this.http
            .post<IJobBid>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(jobBid: IJobBid): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(jobBid);
        return this.http
            .put<IJobBid>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<IJobBid>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IJobBid[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IJobBid[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(jobBid: IJobBid): IJobBid {
        const copy: IJobBid = Object.assign({}, jobBid, {
            wishDate: jobBid.wishDate != null && jobBid.wishDate.isValid() ? jobBid.wishDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.wishDate = res.body.wishDate != null ? moment(res.body.wishDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((jobBid: IJobBid) => {
                jobBid.wishDate = jobBid.wishDate != null ? moment(jobBid.wishDate) : null;
            });
        }
        return res;
    }
}
