#include "all.h"

#include "part.h"

Part::~Part() {
	finalize();

}

Part::Part() {
	rsx = 1.0f;
	rsy = 1.0f;
	sx = 1.0f;
	sy = 1.0f;
	x = 0.0f;
	y = 0.0f;
	z = 0.0f;
	rx = 0.0f;
	ry = 0.0f;
	rz = 0.0f;
	rot = 0.0f;
	rrot = 0.0f;
	a = 0;
	r = 0;
	g = 0;
	b = 0;
	name = 0;
	tx_rsx = 0;
	tx_rsy = 0;
	tx_sx = 0;
	tx_sy = 0;
	tx_x = 0;
	tx_y = 0;
	tx_z = 0;
	tx_rot = 0;
	tx_rx = 0;
	tx_ry = 0;
	tx_rz = 0;
	tx_rrot = 0;
	tx_a = 0;
	tx_r = 0;
	tx_g = 0;
	tx_b = 0;
	tx_type = getType();
	tx_invaliddata = true;
	tx_highlighted = false;
	intransaction = false;
	tx_name = 0;
	coordtap = 0;
	invaliddata = true;
	highlighted = false;
	supTX = true;
	parent = 0;
	isTextSet = false;

  parts++;
  name=(unsigned char*)"";
}


int Part::getNumberOfTextAndFont() {

  return 0;
}


int Part::getTextAndFont(unsigned char** t, int startT) {

  return 0;
}


void Part::setTXSupport(bool b) {

  supTX=b;
}


bool Part::supportsTX() {

  return supTX;
}


bool Part::isInvalid() {

  return invaliddata;
}


void Part::setCoordinateTap(CoordinateTap* ct) {

  coordtap=ct;
}


CoordinateTap* Part::getCoordinateTap() {

  return coordtap;
}


void Part::beginTX() {

  if (intransaction) {
    //throw new RuntimeException((unsigned char*)"Already in transaction: " + name);
  }
  intransaction=true;
  tx_type=getType();
  if (name == 0)   tx_name=(unsigned char *)(unsigned char*)"";
 else   tx_name=name;
  tx_rsx=rsx;
  tx_rsy=rsy;
  tx_sx=sx;
  tx_sy=sy;
  tx_x=x;
  tx_y=y;
  tx_z=z;
  tx_rot=rot;
  tx_a=a;
  tx_r=r;
  tx_g=g;
  tx_b=b;
  tx_rx=rx;
  tx_ry=ry;
  tx_rz=rz;
  tx_rrot=rrot;
  tx_invaliddata=invaliddata;
  tx_highlighted=highlighted;
}


void Part::commitTX() {

  if (!intransaction) {
    //throw new RuntimeException((unsigned char*)"Can not commit: Not in transaction: " + name);
  }
  intransaction=false;
}


void Part::rollbackTX() {

  if (!intransaction) {
    //throw new RuntimeException((unsigned char*)"Can not rollback: Not in transaction: " + name);
  }
  intransaction=false;
  rsx=tx_rsx;
  rsy=tx_rsy;
  sx=tx_sx;
  sy=tx_sy;
  x=tx_x;
  y=tx_y;
  z=tx_z;
  rot=tx_rot;
  a=tx_a;
  r=tx_r;
  g=tx_g;
  b=tx_b;
  rx=tx_rx;
  ry=tx_ry;
  rz=tx_rz;
  rrot=tx_rrot;
  name=tx_name;
  invaliddata=true;
  highlighted=tx_highlighted;
}


float Part::getRsx() {

  return rsx;
}


float Part::getRsy() {

  return rsy;
}


float Part::getSx() {

  return sx;
}


float Part::getSy() {

  return sy;
}


float Part::getX() {

  return x;
}


float Part::getY() {

  return y;
}


float Part::getZ() {

  return z;
}


float Part::getRx() {

  return rx;
}


float Part::getRy() {

  return ry;
}


float Part::getRz() {

  return rz;
}


float Part::getRotation() {

  return rot;
}


float Part::getRrot() {

  return rrot;
}


int Part::calcPhi(float in) {

  int phi=round(in);
  while (phi < 0)   phi+=360;
  phi%=360;
  return phi;
}


unsigned char* Part::getName() {

  return name;
}


void Part::setName(unsigned char* n) {

     if(isTextSet) free(name); name=(unsigned char *)malloc((size_t)(strlen((const char*)n)+1)); strcpy((char*)name, (const char*)n); isTextSet= true;
}


void Part::finalize() {

  parts--;
  coordtap=0;
}


int Part::getNumberOfData() {

  return 0;
}


void Part::setParent(Bone* bone) {

  parent=bone;
}


bool Part::invalidateData() {

  bool i=invaliddata;
  invaliddata=true;
  if (parent != 0)   parent->invaliddata=true;
  return i;
}


void Part::setRoot(float x, float y, float z, float r) {

  invalidateData();
  rx=x;
  ry=y;
  rz=z;
  rrot=r;
}


void Part::rotateRoot(float deg) {

  invalidateData();
  rrot=r;
}


unsigned char* Part::toString() {

  //return(unsigned char*)"Part [rsx=" + rsx + (unsigned char*)", rsy="+ rsy+ (unsigned char*)", sx="+ sx+ (unsigned char*)", sy="+ sy+ (unsigned char*)", x="+ x+ (unsigned char*)", y="+ y+ (unsigned char*)", z="+ z+ (unsigned char*)", rx="+ rx+ (unsigned char*)", ry="+ ry+ (unsigned char*)", rz="+ rz+ (unsigned char*)", rot="+ rot+ (unsigned char*)", rrot="+ rrot+ (unsigned char*)", a="+ a+ (unsigned char*)", r="+ r+ (unsigned char*)", g="+ g+ (unsigned char*)", b="+ b+ (unsigned char*)", name="+ name+ (unsigned char*)", intransaction="+ intransaction+ (unsigned char*)", invaliddata="+ invaliddata+ (unsigned char*)", highlighted="+ highlighted+ (unsigned char*)"]";
}


void Part::setColor(int c) {

  invalidateData();
  a = (unsigned int) (c >> 24);//(int)(c >>> 24);
  r=(int)((c & 0x00FF0000) >> 16);
  g=(int)((c & 0x0000FF00) >> 8);
  b=(int)((c & 0x000000FF));
}


void Part::setAlpha(int alpha) {

  invalidateData();
  a=alpha;
}


void Part::scale(float x, float y) {

  invalidateData();
  sx*=x;
  sy*=y;
}


void Part::scaleRoot(float sx, float sy) {

  invalidateData();
  rsx*=sx;
  rsy*=sy;
}


void Part::clearScale() {

  invalidateData();
  sx=sy=1.0f;
}


void Part::translate(float tx, float ty, float tz) {

  invalidateData();
  x+=tx;
  y+=ty;
  z+=tz;
}


void Part::translateRoot(float tx, float ty, float tz) {

  invalidateData();
  rx+=tx;
  ry+=ty;
  rz+=tz;
}


void Part::clearTranslation() {

  invalidateData();
  x=y=z=0.0f;
}


bool Part::canHaveChilds() {

  return false;
}


int Part::addBCs(BoundingCircle** bcarray, int start) {

  return start;
}


int Part::getNumberOfBCs() {

  return 0;
}


void Part::rotate(float r) {

  rot+=r;
  while (rot < 0.0) {
    rot+=360.0;
  }
  while (rot >= 360.0) {
    rot-=360.0;
  }
  invalidateData();
}


void Part::clearRotation() {

  invalidateData();
  rot=0;
}


void Part::highlight(bool bin) {

  int ored=r;
  int og=g;
  int ob=b;
  invalidateData();
  if (bin && !highlighted) {
    r=(int)(1.3 * r > 255.0 ? 255.0 : 1.3 * r);
    g=(int)(1.3 * g > 255.0 ? 255.0 : 1.3 * g);
    b=(int)(1.3 * b > 255.0 ? 255.0 : 1.3 * b);
    highlighted=true;
  }
 else {
    if (!bin && highlighted) {
      r=ored;
      g=og;
      b=ob;
      highlighted=false;
    }
  }
}


void Part::clearAll() {

  clearTranslation();
  clearScale();
  clearRotation();
}


int Part::getType() {

  return Types::PART;
}


void Part::reset() {

  x=0;
  y=0;
  z=0;
  rot=0;
  sx=sy=1.0f;
  invalidateData();
}


int Part::getRandom(int min, int max) {

  if (min > max) {
    return -1;
  }
  if (min == max) {
    return min;
  }
  double r;
  do {
    r=(((float)rand())/RAND_MAX);
  }
 while (r == 1.0);
  return min + round((float)r * (max - min + 1));
}


int Part::parts = 0;
float Part::mycos[] = {1.0f,0.9998477f,0.99939084f,0.9986295f,0.9975641f,0.9961947f,0.9945219f,0.99254614f,0.99026805f,0.98768836f,0.9848077f,0.98162717f,0.9781476f,0.97437006f,0.9702957f,0.9659258f,0.9612617f,0.9563047f,0.95105654f,0.94551855f,0.9396926f,0.9335804f,0.92718387f,0.92050487f,0.9135454f,0.9063078f,0.89879405f,0.8910065f,0.88294756f,0.8746197f,0.8660254f,0.8571673f,0.8480481f,0.83867055f,0.82903755f,0.81915206f,0.809017f,0.7986355f,0.7880108f,0.777146f,0.76604444f,0.7547096f,0.7431448f,0.7313537f,0.7193398f,0.70710677f,0.6946584f,0.6819984f,0.6691306f,0.656059f,0.64278764f,0.6293204f,0.6156615f,0.60181504f,0.58778524f,0.57357645f,0.5591929f,0.54463905f,0.52991927f,0.5150381f,0.5f,0.4848096f,0.46947157f,0.4539905f,0.43837115f,0.42261827f,0.40673664f,0.39073113f,0.37460658f,0.35836795f,0.34202015f,0.32556817f,0.309017f,0.2923717f,0.27563736f,0.25881904f,0.2419219f,0.22495106f,0.20791169f,0.190809f,0.17364818f,0.15643446f,0.1391731f,0.12186934f,0.104528464f,0.087155744f,0.06975647f,0.052335955f,0.034899496f,0.017452406f,6.123234E-17f,-0.017452406f,-0.034899496f,-0.052335955f,-0.06975647f,-0.087155744f,-0.104528464f,-0.12186934f,-0.1391731f,-0.15643446f,-0.17364818f,-0.190809f,-0.20791169f,-0.22495106f,-0.2419219f,-0.25881904f,-0.27563736f,-0.2923717f,-0.309017f,-0.32556817f,-0.34202015f,-0.35836795f,-0.37460658f,-0.39073113f,-0.40673664f,-0.42261827f,-0.43837115f,-0.4539905f,-0.46947157f,-0.4848096f,-0.5f,-0.5150381f,-0.52991927f,-0.54463905f,-0.5591929f,-0.57357645f,-0.58778524f,-0.60181504f,-0.6156615f,-0.6293204f,-0.64278764f,-0.656059f,-0.6691306f,-0.6819984f,-0.6946584f,-0.70710677f,-0.7193398f,-0.7313537f,-0.7431448f,-0.7547096f,-0.76604444f,-0.777146f,-0.7880108f,-0.7986355f,-0.809017f,-0.81915206f,-0.82903755f,-0.83867055f,-0.8480481f,-0.8571673f,-0.8660254f,-0.8746197f,-0.88294756f,-0.8910065f,-0.89879405f,-0.9063078f,-0.9135454f,-0.92050487f,-0.92718387f,-0.9335804f,-0.9396926f,-0.94551855f,-0.95105654f,-0.9563047f,-0.9612617f,-0.9659258f,-0.9702957f,-0.97437006f,-0.9781476f,-0.98162717f,-0.9848077f,-0.98768836f,-0.99026805f,-0.99254614f,-0.9945219f,-0.9961947f,-0.9975641f,-0.9986295f,-0.99939084f,-0.9998477f,-1.0f,-0.9998477f,-0.99939084f,-0.9986295f,-0.9975641f,-0.9961947f,-0.9945219f,-0.99254614f,-0.99026805f,-0.98768836f,-0.9848077f,-0.98162717f,-0.9781476f,-0.97437006f,-0.9702957f,-0.9659258f,-0.9612617f,-0.9563047f,-0.95105654f,-0.94551855f,-0.9396926f,-0.9335804f,-0.92718387f,-0.92050487f,-0.9135454f,-0.9063078f,-0.89879405f,-0.8910065f,-0.88294756f,-0.8746197f,-0.8660254f,-0.8571673f,-0.8480481f,-0.83867055f,-0.82903755f,-0.81915206f,-0.809017f,-0.7986355f,-0.7880108f,-0.777146f,-0.76604444f,-0.7547096f,-0.7431448f,-0.7313537f,-0.7193398f,-0.70710677f,-0.6946584f,-0.6819984f,-0.6691306f,-0.656059f,-0.64278764f,-0.6293204f,-0.6156615f,-0.60181504f,-0.58778524f,-0.57357645f,-0.5591929f,-0.54463905f,-0.52991927f,-0.5150381f,-0.5f,-0.4848096f,-0.46947157f,-0.4539905f,-0.43837115f,-0.42261827f,-0.40673664f,-0.39073113f,-0.37460658f,-0.35836795f,-0.34202015f,-0.32556817f,-0.309017f,-0.2923717f,-0.27563736f,-0.25881904f,-0.2419219f,-0.22495106f,-0.20791169f,-0.190809f,-0.17364818f,-0.15643446f,-0.1391731f,-0.12186934f,-0.104528464f,-0.087155744f,-0.06975647f,-0.052335955f,-0.034899496f,-0.017452406f,-1.8369701E-16f,0.017452406f,0.034899496f,0.052335955f,0.06975647f,0.087155744f,0.104528464f,0.12186934f,0.1391731f,0.15643446f,0.17364818f,0.190809f,0.20791169f,0.22495106f,0.2419219f,0.25881904f,0.27563736f,0.2923717f,0.309017f,0.32556817f,0.34202015f,0.35836795f,0.37460658f,0.39073113f,0.40673664f,0.42261827f,0.43837115f,0.4539905f,0.46947157f,0.4848096f,0.5f,0.5150381f,0.52991927f,0.54463905f,0.5591929f,0.57357645f,0.58778524f,0.60181504f,0.6156615f,0.6293204f,0.64278764f,0.656059f,0.6691306f,0.6819984f,0.6946584f,0.70710677f,0.7193398f,0.7313537f,0.7431448f,0.7547096f,0.76604444f,0.777146f,0.7880108f,0.7986355f,0.809017f,0.81915206f,0.82903755f,0.83867055f,0.8480481f,0.8571673f,0.8660254f,0.8746197f,0.88294756f,0.8910065f,0.89879405f,0.9063078f,0.9135454f,0.92050487f,0.92718387f,0.9335804f,0.9396926f,0.94551855f,0.95105654f,0.9563047f,0.9612617f,0.9659258f,0.9702957f,0.97437006f,0.9781476f,0.98162717f,0.9848077f,0.98768836f,0.99026805f,0.99254614f,0.9945219f,0.9961947f,0.9975641f,0.9986295f,0.99939084f,0.9998477f};
float Part::mysin[] = {0.0f,0.017452406f,0.034899496f,0.052335955f,0.06975647f,0.087155744f,0.104528464f,0.12186934f,0.1391731f,0.15643446f,0.17364818f,0.190809f,0.20791169f,0.22495106f,0.2419219f,0.25881904f,0.27563736f,0.2923717f,0.309017f,0.32556817f,0.34202015f,0.35836795f,0.37460658f,0.39073113f,0.40673664f,0.42261827f,0.43837115f,0.4539905f,0.46947157f,0.4848096f,0.5f,0.5150381f,0.52991927f,0.54463905f,0.5591929f,0.57357645f,0.58778524f,0.60181504f,0.6156615f,0.6293204f,0.64278764f,0.656059f,0.6691306f,0.6819984f,0.6946584f,0.70710677f,0.7193398f,0.7313537f,0.7431448f,0.7547096f,0.76604444f,0.777146f,0.7880108f,0.7986355f,0.809017f,0.81915206f,0.82903755f,0.83867055f,0.8480481f,0.8571673f,0.8660254f,0.8746197f,0.88294756f,0.8910065f,0.89879405f,0.9063078f,0.9135454f,0.92050487f,0.92718387f,0.9335804f,0.9396926f,0.94551855f,0.95105654f,0.9563047f,0.9612617f,0.9659258f,0.9702957f,0.97437006f,0.9781476f,0.98162717f,0.9848077f,0.98768836f,0.99026805f,0.99254614f,0.9945219f,0.9961947f,0.9975641f,0.9986295f,0.99939084f,0.9998477f,1.0f,0.9998477f,0.99939084f,0.9986295f,0.9975641f,0.9961947f,0.9945219f,0.99254614f,0.99026805f,0.98768836f,0.9848077f,0.98162717f,0.9781476f,0.97437006f,0.9702957f,0.9659258f,0.9612617f,0.9563047f,0.95105654f,0.94551855f,0.9396926f,0.9335804f,0.92718387f,0.92050487f,0.9135454f,0.9063078f,0.89879405f,0.8910065f,0.88294756f,0.8746197f,0.8660254f,0.8571673f,0.8480481f,0.83867055f,0.82903755f,0.81915206f,0.809017f,0.7986355f,0.7880108f,0.777146f,0.76604444f,0.7547096f,0.7431448f,0.7313537f,0.7193398f,0.70710677f,0.6946584f,0.6819984f,0.6691306f,0.656059f,0.64278764f,0.6293204f,0.6156615f,0.60181504f,0.58778524f,0.57357645f,0.5591929f,0.54463905f,0.52991927f,0.5150381f,0.5f,0.4848096f,0.46947157f,0.4539905f,0.43837115f,0.42261827f,0.40673664f,0.39073113f,0.37460658f,0.35836795f,0.34202015f,0.32556817f,0.309017f,0.2923717f,0.27563736f,0.25881904f,0.2419219f,0.22495106f,0.20791169f,0.190809f,0.17364818f,0.15643446f,0.1391731f,0.12186934f,0.104528464f,0.087155744f,0.06975647f,0.052335955f,0.034899496f,0.017452406f,1.2246469E-16f,-0.017452406f,-0.034899496f,-0.052335955f,-0.06975647f,-0.087155744f,-0.104528464f,-0.12186934f,-0.1391731f,-0.15643446f,-0.17364818f,-0.190809f,-0.20791169f,-0.22495106f,-0.2419219f,-0.25881904f,-0.27563736f,-0.2923717f,-0.309017f,-0.32556817f,-0.34202015f,-0.35836795f,-0.37460658f,-0.39073113f,-0.40673664f,-0.42261827f,-0.43837115f,-0.4539905f,-0.46947157f,-0.4848096f,-0.5f,-0.5150381f,-0.52991927f,-0.54463905f,-0.5591929f,-0.57357645f,-0.58778524f,-0.60181504f,-0.6156615f,-0.6293204f,-0.64278764f,-0.656059f,-0.6691306f,-0.6819984f,-0.6946584f,-0.70710677f,-0.7193398f,-0.7313537f,-0.7431448f,-0.7547096f,-0.76604444f,-0.777146f,-0.7880108f,-0.7986355f,-0.809017f,-0.81915206f,-0.82903755f,-0.83867055f,-0.8480481f,-0.8571673f,-0.8660254f,-0.8746197f,-0.88294756f,-0.8910065f,-0.89879405f,-0.9063078f,-0.9135454f,-0.92050487f,-0.92718387f,-0.9335804f,-0.9396926f,-0.94551855f,-0.95105654f,-0.9563047f,-0.9612617f,-0.9659258f,-0.9702957f,-0.97437006f,-0.9781476f,-0.98162717f,-0.9848077f,-0.98768836f,-0.99026805f,-0.99254614f,-0.9945219f,-0.9961947f,-0.9975641f,-0.9986295f,-0.99939084f,-0.9998477f,-1.0f,-0.9998477f,-0.99939084f,-0.9986295f,-0.9975641f,-0.9961947f,-0.9945219f,-0.99254614f,-0.99026805f,-0.98768836f,-0.9848077f,-0.98162717f,-0.9781476f,-0.97437006f,-0.9702957f,-0.9659258f,-0.9612617f,-0.9563047f,-0.95105654f,-0.94551855f,-0.9396926f,-0.9335804f,-0.92718387f,-0.92050487f,-0.9135454f,-0.9063078f,-0.89879405f,-0.8910065f,-0.88294756f,-0.8746197f,-0.8660254f,-0.8571673f,-0.8480481f,-0.83867055f,-0.82903755f,-0.81915206f,-0.809017f,-0.7986355f,-0.7880108f,-0.777146f,-0.76604444f,-0.7547096f,-0.7431448f,-0.7313537f,-0.7193398f,-0.70710677f,-0.6946584f,-0.6819984f,-0.6691306f,-0.656059f,-0.64278764f,-0.6293204f,-0.6156615f,-0.60181504f,-0.58778524f,-0.57357645f,-0.5591929f,-0.54463905f,-0.52991927f,-0.5150381f,-0.5f,-0.4848096f,-0.46947157f,-0.4539905f,-0.43837115f,-0.42261827f,-0.40673664f,-0.39073113f,-0.37460658f,-0.35836795f,-0.34202015f,-0.32556817f,-0.309017f,-0.2923717f,-0.27563736f,-0.25881904f,-0.2419219f,-0.22495106f,-0.20791169f,-0.190809f,-0.17364818f,-0.15643446f,-0.1391731f,-0.12186934f,-0.104528464f,-0.087155744f,-0.06975647f,-0.052335955f,-0.034899496f,-0.017452406f};
