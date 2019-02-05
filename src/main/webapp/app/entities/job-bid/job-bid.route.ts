import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JobBid } from 'app/shared/model/job-bid.model';
import { JobBidService } from './job-bid.service';
import { JobBidComponent } from './job-bid.component';
import { JobBidDetailComponent } from './job-bid-detail.component';
import { JobBidUpdateComponent } from './job-bid-update.component';
import { JobBidDeletePopupComponent } from './job-bid-delete-dialog.component';
import { IJobBid } from 'app/shared/model/job-bid.model';

@Injectable({ providedIn: 'root' })
export class JobBidResolve implements Resolve<IJobBid> {
    constructor(private service: JobBidService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJobBid> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<JobBid>) => response.ok),
                map((jobBid: HttpResponse<JobBid>) => jobBid.body)
            );
        }
        return of(new JobBid());
    }
}

export const jobBidRoute: Routes = [
    {
        path: '',
        component: JobBidComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'kahoulaPrestationApp.jobBid.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: JobBidDetailComponent,
        resolve: {
            jobBid: JobBidResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kahoulaPrestationApp.jobBid.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: JobBidUpdateComponent,
        resolve: {
            jobBid: JobBidResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kahoulaPrestationApp.jobBid.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: JobBidUpdateComponent,
        resolve: {
            jobBid: JobBidResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kahoulaPrestationApp.jobBid.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobBidPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: JobBidDeletePopupComponent,
        resolve: {
            jobBid: JobBidResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kahoulaPrestationApp.jobBid.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
