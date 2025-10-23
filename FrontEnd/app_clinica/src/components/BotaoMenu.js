import { TouchableOpacity, View, Text, Image, StyleSheet, Dimensions } from "react-native";

const screeWidth = Dimensions.get('window').width;
const BUTTON_WIDTH_PERCENTAGE = 0.9;
const buttonWidth = screeWidth * BUTTON_WIDTH_PERCENTAGE
const buttonHeight = buttonWidth * 0.5;

const BotaoMenu = ({icone, titulo, onPress}) => {
    return(
        <TouchableOpacity style={styles.botao} onPress={onPress} activeOpacity={0.7}>
            {icone && (
                <Image style={styles.icone} source={icone} resizeMode="contain"/>
            )}
            <Text>{titulo}</Text>
        </TouchableOpacity>
    )
}

const styles = StyleSheet.create({
    botao:{
        width: buttonWidth,
        height: buttonHeight,
        borderRadius: 15,
        justifyContent:'center',
        alignItems:'center',
        backgroundColor:'#007aff',
        padding:10,
        marginVertical:5,
        alignSelf:'center',
        shadowColor:'#000',
        shadowOffset:{width: 0, height: 2},
        shadowOpacity: 0.25,
        shadowRadius:3.84,
        elevation: 5,
    },
    icone:{
        width: '40%',
        height:'40%',
        marginBottom: 5
    },
    titulo:{
        color:'ffffff',
        fontSize: 14,
        textAlign:'center',
        fontWeight:'bold'
    },
})
export default BotaoMenu;