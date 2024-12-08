export interface PeriodReport {
    itemId: number;
    value: number;
    startTime: string; 
    endTime: string;
    duration: number;
}

export interface PriceDifferenceReport {
    itemId:number;
    name:string;
    currentPrice:number;
    previousPrice:number; // poate fi null
    priceDifference: number;
    tstart: string;
    tend: string;
}

export interface ItemHistoryRecord {
    id :number;
    name :string;
    description :string;
    price :number;
    tStart :string;
    tEnd :string;
    itemId :number;
}