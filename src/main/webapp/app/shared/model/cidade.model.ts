export interface ICidade {
  id?: number;
  descricao?: string;
  estado?: string;
}

export class Cidade implements ICidade {
  constructor(public id?: number, public descricao?: string, public estado?: string) {}
}
