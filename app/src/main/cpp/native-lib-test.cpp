#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_silence_mobile_trainning_MainActivity_stringFromJNITest(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++ native-lib-test";
    return env->NewStringUTF(hello.c_str());
}
