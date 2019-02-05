import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'region',
                loadChildren: './region/region.module#KahoulaPrestationRegionModule'
            },
            {
                path: 'city',
                loadChildren: './city/city.module#KahoulaPrestationCityModule'
            },
            {
                path: 'location',
                loadChildren: './location/location.module#KahoulaPrestationLocationModule'
            },
            {
                path: 'customer',
                loadChildren: './customer/customer.module#KahoulaPrestationCustomerModule'
            },
            {
                path: 'partner',
                loadChildren: './partner/partner.module#KahoulaPrestationPartnerModule'
            },
            {
                path: 'job-bid',
                loadChildren: './job-bid/job-bid.module#KahoulaPrestationJobBidModule'
            },
            {
                path: 'bill',
                loadChildren: './bill/bill.module#KahoulaPrestationBillModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KahoulaPrestationEntityModule {}
