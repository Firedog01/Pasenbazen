package pl.lodz.p.edu.cassandra.model.EQ;

public class Lens extends Equipment{

        private String focalLength;

        public Lens(double firstDayCost,
                    double nextDaysCost,
                    double bail,
                    String name,
                    String focalLength
        ) throws EquipmentException {
            super(firstDayCost, nextDaysCost, bail, name);
            this.focalLength = focalLength;
        }

        protected Lens() {}


        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Lens{");
            sb.append("focalLength='").append(focalLength).append('\'');
            sb.append('}');
            return super.toString() + sb.toString();
        }

        public String getFocalLength() {
            return focalLength;
        }

        public void setFocalLength(String focalLength) {
            this.focalLength = focalLength;
        }
}
