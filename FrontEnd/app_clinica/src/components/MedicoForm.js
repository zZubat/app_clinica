import React, { useState, useEffect }from "react";
import { View, Text, TextInput, ScrollView, StyleSheet, TouchableOpacity, Alert, Platform, Picker } from "react-native";

const especialidades = ['Cardiologia', 'Dermatologia', 'Ginecologia', 'Ortopedia'];
const initialMedicoState = {
    nome:'',
    especialidade:especialidades[0],
    crm:'',
    email:'',
    telefone:'',
    logradouro:'',
    numero:'',
    complemento:'',
    cidade:'',
    uf:'',
    cep:''
}
const MedicoForm = ({medico, onSave, onCancel, navigation}) =>{
    const [formData, setFormData] = useState(medico || initialMedicoState);
}

const [erros, setErros] = useState({});

const isEditing = !!medico;
const buttonTitle = isEditing ? 'Concluir Edição' : 'Concluir Cadastro';
const requireFields = ['nome', 'especialidade', 'crm', 'telefone', 'logradouro', 'numero', 'uf', 'cep'];
useEffect(() => {setFormData(medico||initialMedicoState)},[medico]) 