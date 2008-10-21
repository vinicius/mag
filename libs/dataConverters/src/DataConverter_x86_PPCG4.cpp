#include "DataConverter_x86_PPCG4.hpp"

#include <iostream>

vector<datasizes_t> *DataConverter_x86_PPCG4::createDataSizes( ) {

  vector<datasizes_t> *dataSizes = 
    new vector<datasizes_t>(2*CKPT_UNSIGNED, &DataConverter_x86_PPCG4::getSizesError);
  
  (*dataSizes)[CKPT_BOOL]    = &DataConverter_x86_PPCG4::getSizesSignedBool;
  (*dataSizes)[CKPT_CHAR]    = &DataConverter_x86_PPCG4::getSizesSignedChar;
  (*dataSizes)[CKPT_WCHAR]   = &DataConverter_x86_PPCG4::getSizesSignedWChar;
  (*dataSizes)[CKPT_SHORT]   = &DataConverter_x86_PPCG4::getSizesSignedShort;
  (*dataSizes)[CKPT_INT]     = &DataConverter_x86_PPCG4::getSizesSignedInt;
  (*dataSizes)[CKPT_LONG]    = &DataConverter_x86_PPCG4::getSizesSignedLong;
  (*dataSizes)[CKPT_FLOAT]   = &DataConverter_x86_PPCG4::getSizesSignedFloat;
  (*dataSizes)[CKPT_DOUBLE]  = &DataConverter_x86_PPCG4::getSizesSignedDouble;
  (*dataSizes)[CKPT_LDOUBLE] = &DataConverter_x86_PPCG4::getSizesSignedLongDouble;
  (*dataSizes)[CKPT_FPOS_T]  = &DataConverter_x86_PPCG4::getSizesFilePos;
  
  (*dataSizes)[CKPT_CHAR+CKPT_UNSIGNED]  = &DataConverter_x86_PPCG4::getSizesUnsignedChar;
  (*dataSizes)[CKPT_SHORT+CKPT_UNSIGNED] = &DataConverter_x86_PPCG4::getSizesUnsignedShort;
  (*dataSizes)[CKPT_INT+CKPT_UNSIGNED]   = &DataConverter_x86_PPCG4::getSizesUnsignedInt;
  (*dataSizes)[CKPT_LONG+CKPT_UNSIGNED]  = &DataConverter_x86_PPCG4::getSizesUnsignedLong;
  
  return dataSizes;
  
}

vector<dataconverter_t> *DataConverter_x86_PPCG4::createDataConverter ( ) {
  
  vector<dataconverter_t> *dataConverter = 
    new vector<dataconverter_t>(2*CKPT_UNSIGNED, &DataConverter_x86_PPCG4::readError);
  
  (*dataConverter)[CKPT_BOOL]    = &DataConverter_x86_PPCG4::readSignedBool;
  (*dataConverter)[CKPT_CHAR]    = &DataConverter_x86_PPCG4::readSignedChar;
  (*dataConverter)[CKPT_WCHAR]   = &DataConverter_x86_PPCG4::readSignedWChar;
  (*dataConverter)[CKPT_SHORT]   = &DataConverter_x86_PPCG4::readSignedShort;
  (*dataConverter)[CKPT_INT]     = &DataConverter_x86_PPCG4::readSignedInt;
  (*dataConverter)[CKPT_LONG]    = &DataConverter_x86_PPCG4::readSignedLong;
  (*dataConverter)[CKPT_FLOAT]   = &DataConverter_x86_PPCG4::readSignedFloat;
  (*dataConverter)[CKPT_DOUBLE]  = &DataConverter_x86_PPCG4::readSignedDouble;
  (*dataConverter)[CKPT_LDOUBLE] = &DataConverter_x86_PPCG4::readSignedLongDouble;
  (*dataConverter)[CKPT_FPOS_T]  = &DataConverter_x86_PPCG4::readFilePos;
  
  (*dataConverter)[CKPT_CHAR+CKPT_UNSIGNED]  = &DataConverter_x86_PPCG4::readUnsignedChar;
  (*dataConverter)[CKPT_SHORT+CKPT_UNSIGNED] = &DataConverter_x86_PPCG4::readUnsignedShort;
  (*dataConverter)[CKPT_INT+CKPT_UNSIGNED]   = &DataConverter_x86_PPCG4::readUnsignedInt;
  (*dataConverter)[CKPT_LONG+CKPT_UNSIGNED]  = &DataConverter_x86_PPCG4::readUnsignedLong;
  
  return dataConverter;
}

  long DataConverter_x86_PPCG4::readError(void *dst, const void *src) {
    std::cerr << "Type not defined!!!!" << endl;
    memcpy(dst, src, 1);
    return 1;
  }

  long DataConverter_x86_PPCG4::readFilePos(void *dst, const void *src) {
    memcpy((char *)dst+4,(char *)src+3, 1);
    memcpy((char *)dst+5,(char *)src+2, 1);
    memcpy((char *)dst+6,(char *)src+1, 1);
    memcpy((char *)dst+7,(char *)src,   1);
    //    cout << "FilePos Read!!!!" << *(double *)dst << endl;

    return CKPT_FPOS_T_X86_SIZE;
  }

//----------- Signed converters -----------------------------
  long DataConverter_x86_PPCG4::readSignedBool(void *dst, const void *src) {
    memcpy((char *)dst+3, src, 1);
    return CKPT_BOOL_X86_SIZE;
  }
  
  long DataConverter_x86_PPCG4::readSignedChar(void *dst, const void *src) {
    memcpy(dst, src, CKPT_CHAR_PPCG4_SIZE);
    //    cout << "Char Read!!!!" << *(char *)dst << endl;
    return CKPT_CHAR_X86_SIZE;
  }
  
  long DataConverter_x86_PPCG4::readSignedWChar(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+1, 1);
    memcpy((char *)dst+1,(char *)src,   1);
    //    cout << " Read!!!!" << *(wchar_t *)dst << endl;

    return CKPT_WCHAR_X86_SIZE;
  }
  
  long DataConverter_x86_PPCG4::readSignedShort(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+1, 1);
    memcpy((char *)dst+1,(char *)src,   1);
    //    cout << "Short Read!!!!" << *(short *)dst << endl;

    return CKPT_SHORT_X86_SIZE;
  }
  
  long DataConverter_x86_PPCG4::readSignedInt(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+3, 1);
    memcpy((char *)dst+1,(char *)src+2, 1);
    memcpy((char *)dst+2,(char *)src+1, 1);
    memcpy((char *)dst+3,(char *)src  , 1);
    //    cout << "Int Read!!!!" << *(int *)dst << endl;

    return CKPT_INT_X86_SIZE;
  }
  
  long DataConverter_x86_PPCG4::readSignedLong(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+3, 1);
    memcpy((char *)dst+1,(char *)src+2, 1);
    memcpy((char *)dst+2,(char *)src+1, 1);
    memcpy((char *)dst+3,(char *)src  , 1);
    //    cout << "Long Read!!!!" << *(long *)dst << endl;

    return CKPT_LONG_X86_SIZE;
  }
  
  long DataConverter_x86_PPCG4::readSignedFloat(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+3, 1);
    memcpy((char *)dst+1,(char *)src+2, 1);
    memcpy((char *)dst+2,(char *)src+1, 1);
    memcpy((char *)dst+3,(char *)src  , 1);
    //    cout << "Float Read!!!!" << *(float *)dst << endl;

    return CKPT_FLOAT_X86_SIZE;
  }
  
  long DataConverter_x86_PPCG4::readSignedDouble(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+7, 1);
    memcpy((char *)dst+1,(char *)src+6, 1);
    memcpy((char *)dst+2,(char *)src+5, 1);
    memcpy((char *)dst+3,(char *)src+4, 1);
    memcpy((char *)dst+4,(char *)src+3, 1);
    memcpy((char *)dst+5,(char *)src+2, 1);
    memcpy((char *)dst+6,(char *)src+1, 1);
    memcpy((char *)dst+7,(char *)src,   1);
    //    cout << "Double Read!!!!" << *(double *)dst << endl;

    return CKPT_DOUBLE_X86_SIZE;
  }
  
  long DataConverter_x86_PPCG4::readSignedLongDouble(void *dst, const void *src) {
    // Read the mantissa (52, 11, 1023) x (64, 15, 16383)
    // FIXME: Test for overflow!!!
    unsigned int a1, b1;
    memcpy(((char *)&a1),  (char *)src+7, 1);
    memcpy(((char *)&a1)+1,(char *)src+6, 1);
    memcpy(((char *)&a1)+2,(char *)src+5, 1);
    memcpy(((char *)&a1)+3,(char *)src+4, 1);
    a1 = a1 >> 3; // First bit in 10 bytes x86 mantissa is always 1

    memcpy(((char *)&b1),  (char *)src+4, 1);
    memcpy(((char *)&b1)+1,(char *)src+3, 1);
    memcpy(((char *)&b1)+2,(char *)src+2, 1);
    memcpy(((char *)&b1)+3,(char *)src+1, 1);
    b1 = b1 << 5;

    memcpy((char *)dst+1, &a1, 4);
    memcpy((char *)dst+5, &b1, 3);

    // Read the exponent
    short a, s; // 16 bits
    memcpy(((char *)&a)+1, (char *)src+8, 1);
    memcpy(((char *)&a),   (char *)src+9, 1);
    (a>=0) ? s = 0x0000 : s = 0x8000; // reads the signal
    a = a & 0x7FFF; // Remove sign bit
    a -= 16383;     // Remove bias
    a = a & 0x07FF; // Remove extra bits (now only 11) - Check overflow???
    a += 1023;      // Add the new bias
    a = a << 4;
    a = a | s;      // Adds the sign byte
    memcpy(&s, (char *)dst, 2);
    s = (s & 0x000f) | a;
    memcpy((char *)dst, &s, 2);

    //    cout << "Long Double Read!!!!" << *(long double *)dst << endl;
    return CKPT_LDOUBLE_X86_SIZE;
  }
  
//----------- Unsigned converters ---------------------------
  long DataConverter_x86_PPCG4::readUnsignedChar(void *dst, const void *src) {
    memcpy(dst, src, CKPT_CHAR_PPCG4_SIZE);
    //    cout << "UChar Read!!!!" << *(unsigned char *)dst << endl;
    return CKPT_CHAR_X86_SIZE;
  }
  
  long DataConverter_x86_PPCG4::readUnsignedShort(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+1, 1);
    memcpy((char *)dst+1,(char *)src,   1);
    //    cout << "UShort Read!!!!" << *(unsigned short *)dst << endl;

    return CKPT_SHORT_X86_SIZE;
  }
  
  long DataConverter_x86_PPCG4::readUnsignedInt(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+3, 1);
    memcpy((char *)dst+1,(char *)src+2, 1);
    memcpy((char *)dst+2,(char *)src+1, 1);
    memcpy((char *)dst+3,(char *)src  , 1);
    //    cout << "UInt Read!!!!" << *(unsigned int *)dst << endl;

    return CKPT_INT_X86_SIZE;
  }
  
  long DataConverter_x86_PPCG4::readUnsignedLong(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+3, 1);
    memcpy((char *)dst+1,(char *)src+2, 1);
    memcpy((char *)dst+2,(char *)src+1, 1);
    memcpy((char *)dst+3,(char *)src  , 1);
    //    cout << "ULong Read!!!!" << *(unsigned long *)dst << endl;

    return CKPT_LONG_X86_SIZE;
  }

//==========================================================================

void DataConverter_x86_PPCG4::getSizesError(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = 1;
  *src = 1;
}

void DataConverter_x86_PPCG4::getSizesFilePos(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_FPOS_T_PPCG4_SIZE;
  *src = CKPT_FPOS_T_X86_SIZE;
}

//----------- Signed converters -----------------------------
void DataConverter_x86_PPCG4::getSizesSignedBool(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_BOOL_PPCG4_SIZE;
  *src = CKPT_BOOL_X86_SIZE;
}

void DataConverter_x86_PPCG4::getSizesSignedChar(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_CHAR_PPCG4_SIZE;
  *src = CKPT_CHAR_X86_SIZE;
}

void DataConverter_x86_PPCG4::getSizesSignedWChar(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_WCHAR_PPCG4_SIZE;
  *src = CKPT_WCHAR_X86_SIZE;
}

void DataConverter_x86_PPCG4::getSizesSignedShort(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_SHORT_PPCG4_SIZE;
  *src = CKPT_SHORT_X86_SIZE;
}

void DataConverter_x86_PPCG4::getSizesSignedInt(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_INT_PPCG4_SIZE;
  *src = CKPT_INT_X86_SIZE;
}

void DataConverter_x86_PPCG4::getSizesSignedLong(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_LONG_PPCG4_SIZE;
  *src = CKPT_LONG_X86_SIZE;
}

void DataConverter_x86_PPCG4::getSizesSignedFloat(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_FLOAT_PPCG4_SIZE;
  *src = CKPT_FLOAT_X86_SIZE;
}

void DataConverter_x86_PPCG4::getSizesSignedDouble(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_DOUBLE_PPCG4_SIZE;
  *src = CKPT_DOUBLE_X86_SIZE;
}

void DataConverter_x86_PPCG4::getSizesSignedLongDouble(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_LDOUBLE_PPCG4_SIZE;
  *src = CKPT_LDOUBLE_X86_SIZE;
}

//----------- Unsigned converters ---------------------------
void DataConverter_x86_PPCG4::getSizesUnsignedChar(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_CHAR_PPCG4_SIZE;
  *src = CKPT_CHAR_X86_SIZE;
}    

void DataConverter_x86_PPCG4::getSizesUnsignedShort(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_SHORT_PPCG4_SIZE;
  *src = CKPT_SHORT_X86_SIZE;
}

void DataConverter_x86_PPCG4::getSizesUnsignedInt(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_INT_PPCG4_SIZE;
  *src = CKPT_INT_X86_SIZE;
}

void DataConverter_x86_PPCG4::getSizesUnsignedLong(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_LONG_PPCG4_SIZE;
  *src = CKPT_LONG_X86_SIZE;
}
