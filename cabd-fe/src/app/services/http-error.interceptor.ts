import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        // Handle HTTP errors
        let errorMessage = 'An unknown error occurred!';
        if (error.error instanceof ErrorEvent) {
          // Client-side errors
          errorMessage = `Client-side error: ${error.error.message}`;
        } else {
          // Server-side errors
          errorMessage = `Server-side error: ${error.status} - ${error.error}`;
        }
        console.error(error);

        // Optionally, show a user-friendly message
        //alert(errorMessage);

        return throwError(errorMessage); // Pass the error to the component
      })
    );
  }
}
