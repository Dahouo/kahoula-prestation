import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KahoulaPrestationSharedModule } from 'app/shared';
import {
    JobBidComponent,
    JobBidDetailComponent,
    JobBidUpdateComponent,
    JobBidDeletePopupComponent,
    JobBidDeleteDialogComponent,
    jobBidRoute,
    jobBidPopupRoute
} from './';

const ENTITY_STATES = [...jobBidRoute, ...jobBidPopupRoute];

@NgModule({
    imports: [KahoulaPrestationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [JobBidComponent, JobBidDetailComponent, JobBidUpdateComponent, JobBidDeleteDialogComponent, JobBidDeletePopupComponent],
    entryComponents: [JobBidComponent, JobBidUpdateComponent, JobBidDeleteDialogComponent, JobBidDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KahoulaPrestationJobBidModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
